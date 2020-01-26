package com.github.liangyu.earthquakes.entities

data class Earthquake(
    val datetime: DateTime,
    val depth: Int,
    val eqid: String,
    val lat: Double,
    val lng: Double,
    val magnitude: Double,
    val src: String
)