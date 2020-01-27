package com.github.liangyu.earthquakes.ui.model

import com.github.liangyu.earthquakes.common.DateTime

data class Earthquake(
    val eqid: String,
    val datetime: DateTime,
    val depth: Double,
    val lat: Double,
    val lng: Double,
    val magnitude: Double,
    val src: String,
    val major: Boolean
)
