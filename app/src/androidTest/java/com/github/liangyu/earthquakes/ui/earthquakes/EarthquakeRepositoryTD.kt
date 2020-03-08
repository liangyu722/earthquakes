package com.github.liangyu.earthquakes.ui.earthquakes

import com.github.liangyu.earthquakes.common.Result
import com.github.liangyu.earthquakes.data.EarthquakeEntity
import com.github.liangyu.earthquakes.data.EarthquakeRepository
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class EarthquakeRepositoryTD @Inject constructor() : EarthquakeRepository {

    private var listResult: Result<List<EarthquakeEntity>>? = null

    fun addEarthQuakeToList(earthquake: EarthquakeEntity) = runBlocking {
        val list = getEarthquakeEntityListFromListResult().toMutableList()
        list += earthquake
        listResult = Result.Success(list)
    }

    private fun getEarthquakeEntityListFromListResult(): List<EarthquakeEntity> {
        if (listResult == null || listResult is Result.Error) return emptyList()
        return (listResult as Result.Success).data
    }

    override suspend fun getEarthquakes(forceUpdate: Boolean): Result<List<EarthquakeEntity>> {
        if (listResult == null) {
            throw Exception("ListResult is not set")
        }
        return listResult!!
    }

    override suspend fun getEarthquake(
        eqid: String,
        forceUpdate: Boolean
    ): Result<EarthquakeEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}