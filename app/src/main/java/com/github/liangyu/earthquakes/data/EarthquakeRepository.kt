package com.github.liangyu.earthquakes.data

interface EarthquakeRepository {

    suspend fun getEarthquakes(forceUpdate: Boolean = false): Result<List<Earthquake>>

    suspend fun getEarthquake(eqid: String, forceUpdate: Boolean = false): Result<Earthquake>
}