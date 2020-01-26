package com.github.liangyu.earthquakes.data.response

data class Earthquake(
    val datetime: DateTime,
    val depth: Double,
    val eqid: String,
    val lat: Double,
    val lng: Double,
    val magnitude: Double,
    val src: String
)