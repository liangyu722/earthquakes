package com.github.liangyu.earthquakes.data

import com.github.liangyu.earthquakes.common.RepositoryLoadingException
import com.github.liangyu.earthquakes.common.Result
import com.github.liangyu.earthquakes.common.Result.*
import com.github.liangyu.earthquakes.data.networking.GeoNameEarthquakeService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import javax.inject.Inject

class DefaultEarthquakeRepository @Inject constructor(
    private val geoNameEarthquakeService: GeoNameEarthquakeService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): EarthquakeRepository {

    private var cachedEarthquakeEntity: ConcurrentMap<String, EarthquakeEntity>? = null

    override suspend fun getEarthquakes(forceUpdate: Boolean): Result<List<EarthquakeEntity>> {
        return withContext(ioDispatcher) {
            if (!forceUpdate) {
                cachedEarthquakeEntity?.let { cachedEarthquakes ->
                    return@withContext Success(cachedEarthquakes.values.sortedBy { it.datetime })
                }
            }

            val newEarthquakes = fetchEarthquakesFromRemote()
            (newEarthquakes as? Success)?.let { refreshCache(it.data) }
            cachedEarthquakeEntity?.values?.let { tasks ->
                return@withContext Success(tasks.sortedBy { it.datetime })
            }

            (newEarthquakes as? Success)?.let {
                if (it.data.isEmpty()) {
                    return@withContext Success(it.data)
                }
            }

            return@withContext Error(RepositoryLoadingException("Cannot load earthquake"))
        }
    }

    override suspend fun getEarthquake(eqid: String, forceUpdate: Boolean): Result<EarthquakeEntity> {
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

            return@withContext Error(RepositoryLoadingException("Cannot find earthquake"))
        }
    }

    private suspend fun fetchEarthquakesFromRemote(): Result<List<EarthquakeEntity>> {
        return try {
            val earthquake = geoNameEarthquakeService.earthquakeAsync().await()
            Success(earthquake.earthquakeEntities)
        } catch (e: Exception) {
            Error(e)
        }
    }

    private fun getEarthquakeWithId(id: String) = cachedEarthquakeEntity?.get(id)

    private fun refreshCache(earthquakeEntities: List<EarthquakeEntity>) {
        cachedEarthquakeEntity?.clear()
        earthquakeEntities.sortedBy { it.datetime }.forEach {
            cacheEarthquake(it)
        }
    }

    private fun cacheEarthquake(earthquakeEntity: EarthquakeEntity) {
        if (cachedEarthquakeEntity == null) {
            cachedEarthquakeEntity = ConcurrentHashMap()
        }
        cachedEarthquakeEntity?.put(earthquakeEntity.eqid, earthquakeEntity)
    }
}