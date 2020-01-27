package com.github.liangyu.earthquakes.data.networking.response

import com.github.liangyu.earthquakes.data.EarthquakeEntity

data class EarthquakeResponse(
    val earthquakeEntities: List<EarthquakeEntity>
)