package com.github.liangyu.earthquakes.ui.earthquakes

import androidx.lifecycle.*
import com.github.liangyu.earthquakes.Event
import com.github.liangyu.earthquakes.R
import com.github.liangyu.earthquakes.data.Earthquake
import com.github.liangyu.earthquakes.data.EarthquakeRepository
import com.github.liangyu.earthquakes.data.Result.Success
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class EarthquakesViewModel @Inject constructor(
    private val repository: EarthquakeRepository
) : ViewModel() {

    private val _items = MutableLiveData<List<Earthquake>>().apply { value = emptyList() }
    val items: LiveData<List<Earthquake>> = _items

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarMessage: LiveData<Event<Int>> = _snackbarText

    private val _openEarthquakeEvent = MutableLiveData<Event<String>>()
    val openEarthquakeEvent: LiveData<Event<String>> = _openEarthquakeEvent

    val empty: LiveData<Boolean> = Transformations.map(_items) {
        it.isEmpty()
    }

    init {
        loadEarthquakes(true)
    }

    /**
     * Called by Data Binding.
     */
    fun openEarthquake(eqId: String) {
        _openEarthquakeEvent.value = Event(eqId)
    }

    //Only ever going to be error message, but what if we want to add more later
    private fun showSnackbarMessage(message: Int) {
        _snackbarText.value = Event(message)
    }

    fun loadEarthquakes(forceUpdate: Boolean) {
        _dataLoading.value = true
        viewModelScope.launch {
            val earthquakeResult = repository.getEarthquakes(forceUpdate)
            if (earthquakeResult is Success) {
                val earthquakes = earthquakeResult.data
                _items.value = ArrayList(earthquakes)
            } else {
                _items.value = emptyList()
                showSnackbarMessage(R.string.loading_earthquake_error)
            }
            _dataLoading.value = false
        }
    }

    fun refresh() {
        loadEarthquakes(true)
    }
}