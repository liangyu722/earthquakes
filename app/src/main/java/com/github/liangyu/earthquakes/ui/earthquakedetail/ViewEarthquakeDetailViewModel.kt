package com.github.liangyu.earthquakes.ui.earthquakedetail

import androidx.lifecycle.LiveData
import com.github.liangyu.earthquakes.common.Event
import com.github.liangyu.earthquakes.ui.model.Earthquake


interface ViewEarthquakeDetailViewModel  {

    val earthquake: LiveData<Earthquake>
    val dataLoading: LiveData<Boolean>
    val snackbarMessage: LiveData<Event<Int>>

    fun start(eqid: String)
}
