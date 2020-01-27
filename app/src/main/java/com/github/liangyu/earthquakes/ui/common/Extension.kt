package com.github.liangyu.earthquakes.ui.common

import com.github.liangyu.earthquakes.data.EarthquakeEntity
import com.github.liangyu.earthquakes.ui.model.Earthquake

fun EarthquakeEntity.toEarthQuake(): Earthquake {
    return Earthquake(
        this.eqid,
        this.datetime,
        this.depth,
        this.lat,
        this.lng,
        this.magnitude,
        this.src,
        this.magnitude >= 8.0
    )
}
