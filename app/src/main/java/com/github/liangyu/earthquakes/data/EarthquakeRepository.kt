package com.github.liangyu.earthquakes.data

import com.github.liangyu.earthquakes.common.Result

interface EarthquakeRepository {

    suspend fun getEarthquakes(forceUpdate: Boolean = false): Result<List<EarthquakeEntity>>

    suspend fun getEarthquake(eqid: String, forceUpdate: Boolean = false): Result<EarthquakeEntity>
}