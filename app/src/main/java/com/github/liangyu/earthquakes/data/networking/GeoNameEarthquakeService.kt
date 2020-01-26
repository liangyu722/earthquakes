package com.github.liangyu.earthquakes.data.networking

import com.github.liangyu.earthquakes.data.networking.response.EarthquakeResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoNameEarthquakeService {

    @GET("earthquakesJSON")
    fun earthquakeAsync(
        @Query("north") northCoordinate: Double = 44.1,
        @Query("south") southCoordinate: Double = -9.9,
        @Query("east") eastCoordinate: Double = -22.4,
        @Query("west") westCoordinate: Double = 55.2
    ): Deferred<EarthquakeResponse>
}