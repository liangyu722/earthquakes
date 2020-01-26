package com.github.liangyu.earthquakes.data.networking.response

import com.github.liangyu.earthquakes.data.db.entity.Earthquake

data class EarthquakeResponse(
    val earthquakes: List<Earthquake>
)