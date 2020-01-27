package com.github.liangyu.earthquakes.ui.earthquakedetail

import androidx.annotation.DimenRes
import androidx.databinding.BindingAdapter
import com.github.liangyu.earthquakes.ui.model.Earthquake
import com.github.liangyu.earthquakes.util.getFloatUsingCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng


@BindingAdapter("app:mapCamera")
fun setCamera(mapView: MapView, earthquake: Earthquake?) {
    if (earthquake != null) {
        mapView.getMapAsync { map ->
            val position = CameraPosition.Builder().target(LatLng(earthquake.lat, earthquake.lng))
                .build()
            map.animateCamera(CameraUpdateFactory.newCameraPosition(position))
        }
    }
}

@BindingAdapter("mapMinZoom", "mapMaxZoom", requireAll = true)
fun mapZoomLevels(mapView: MapView, @DimenRes minZoomResId: Int, @DimenRes maxZoomResId: Int) {
    val minZoom = mapView.resources.getFloatUsingCompat(minZoomResId)
    val maxZoom = mapView.resources.getFloatUsingCompat(maxZoomResId)
    mapView.getMapAsync {
        it.setMinZoomPreference(minZoom)
        it.setMaxZoomPreference(maxZoom)
    }
}