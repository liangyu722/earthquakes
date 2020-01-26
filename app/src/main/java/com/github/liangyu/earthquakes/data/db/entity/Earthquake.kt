package com.github.liangyu.earthquakes.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.liangyu.earthquakes.common.DateTime

@Entity(tableName = "earthquake")
data class Earthquake(
    @PrimaryKey val eqid: String,
    val datetime: DateTime,
    val depth: Double,
    val lat: Double,
    val lng: Double,
    val magnitude: Double,
    val src: String
)