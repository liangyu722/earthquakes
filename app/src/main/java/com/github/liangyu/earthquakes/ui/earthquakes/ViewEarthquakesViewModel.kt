package com.github.liangyu.earthquakes.ui.earthquakes

import androidx.lifecycle.LiveData
import com.github.liangyu.earthquakes.common.Event
import com.github.liangyu.earthquakes.ui.model.Earthquake

interface ViewEarthquakesViewModel {

    val items: LiveData<List<Earthquake>>
    val dataLoading: LiveData<Boolean>
    val snackbarMessage: LiveData<Event<Int>>
    val openEarthquakeEvent: LiveData<Event<String>>
    val empty: LiveData<Boolean>
    fun openEarthquake(eqId: String)
    fun loadEarthquakes(forceUpdate: Boolean)
    fun refresh()
}