package com.github.liangyu.earthquakes.data

import com.github.liangyu.earthquakes.data.Result.*
import com.github.liangyu.earthquakes.data.networking.GeoNameEarthquakeService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class DefaultEarthquakeRepository(
    private val geoNameEarthquakeService: GeoNameEarthquakeService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): EarthquakeRepository {

    private var cachedEarthquake: ConcurrentMap<String, Earthquake>? = null

    override suspend fun getEarthquakes(forceUpdate: Boolean): Result<List<Earthquake>> {
        return withContext(ioDispatcher) {
            if (!forceUpdate) {
                cachedEarthquake?.let { cachedEarthquakes ->
                    return@withContext Success(cachedEarthquakes.values.sortedBy { it.datetime })
                }
            }

            val newEarthquakes = fetchEarthquakesFromRemote()
            (newEarthquakes as? Success)?.let { refreshCache(it.data) }
            cachedEarthquake?.values?.let { tasks ->
                return@withContext Success(tasks.sortedBy { it.datetime })
            }

            (newEarthquakes as? Success)?.let {
                if (it.data.isEmpty()) {
                    return@withContext Success(it.data)
                }
            }

            return@withContext Error(Exception("Illegal state"))
        }
    }

    override suspend fun getEarthquake(eqid: String, forceUpdate: Boolean): Result<Earthquake> {
        return withContext(ioDispatcher) {
            // Respond immediately with cache if available
            if (!forceUpdate) {
                getEarthquakeWithId(eqid)?.let {
                    return@withContext Success(it)
                }
            }

            val newEarthquakes = fetchEarthquakesFromRemote()
            (newEarthquakes as? Success)?.let { refreshCache(it.data) }
            getEarthquakeWithId(eqid)?.let {
                return@withContext Success(it)
            }

            return@withContext Error(Exception("Cannot find earthquake"))
        }
    }

    private suspend fun fetchEarthquakesFromRemote(): Result<List<Earthquake>> {
        return try {
            val earthquake = geoNameEarthquakeService.earthquakeAsync().await()
            Success(earthquake.earthquakes)
        } catch (e: Exception) {
            Error(e)
        }
    }

    private fun getEarthquakeWithId(id: String) = cachedEarthquake?.get(id)

    private fun refreshCache(earthquakes: List<Earthquake>) {
        cachedEarthquake?.clear()
        earthquakes.sortedBy { it.datetime }.forEach {
            cacheEarthquake(it)
        }
    }

    private fun cacheEarthquake(earthquake: Earthquake) {
        if (cachedEarthquake == null) {
            cachedEarthquake = ConcurrentHashMap()
        }
        cachedEarthquake?.put(earthquake.eqid, earthquake)
    }
}