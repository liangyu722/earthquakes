package com.github.liangyu.earthquakes.ui.earthquakes

import androidx.lifecycle.*
import com.github.liangyu.earthquakes.common.Event
import com.github.liangyu.earthquakes.R
import com.github.liangyu.earthquakes.common.Result.Success
import com.github.liangyu.earthquakes.data.EarthquakeRepository
import com.github.liangyu.earthquakes.ui.common.toEarthQuake
import com.github.liangyu.earthquakes.ui.model.Earthquake
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class EarthquakesViewModel @Inject constructor(
    private val repository: EarthquakeRepository
) : ViewModel(), ViewEarthquakesViewModel {

    override val items = MutableLiveData<List<Earthquake>>().apply { value = emptyList() }
    override val dataLoading = MutableLiveData<Boolean>()
    override val snackbarMessage = MutableLiveData<Event<Int>>()
    override val openEarthquakeEvent = MutableLiveData<Event<String>>()
    override val empty: LiveData<Boolean> = Transformations.map(items) {
        it.isEmpty()
    }

    init {
        loadEarthquakes(true)
    }

    override fun openEarthquake(eqId: String) {
        openEarthquakeEvent.value = Event(eqId)
    }

    //Only ever going to be error message, but what if we want to add more later
    private fun showSnackbarMessage(message: Int) {
        snackbarMessage.value = Event(message)
    }

    override fun loadEarthquakes(forceUpdate: Boolean) {
        dataLoading.value = true
        viewModelScope.launch {
            val earthquakeResult = repository.getEarthquakes(forceUpdate)
            if (earthquakeResult is Success) {
                val earthquakes = earthquakeResult.data.map {
                    it.toEarthQuake()
                }
                items.value = ArrayList(earthquakes)
            } else {
                items.value = emptyList()
                showSnackbarMessage(R.string.loading_earthquake_error)
            }
            dataLoading.value = false
        }
    }

    override fun refresh() {
        loadEarthquakes(true)
    }
}
