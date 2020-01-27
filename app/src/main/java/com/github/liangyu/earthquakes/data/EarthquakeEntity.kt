package com.github.liangyu.earthquakes.data

import com.github.liangyu.earthquakes.common.DateTime

data class EarthquakeEntity(
    val eqid: String,
    val datetime: DateTime,
    val depth: Double,
    val lat: Double,
    val lng: Double,
    val magnitude: Double,
    val src: String
)