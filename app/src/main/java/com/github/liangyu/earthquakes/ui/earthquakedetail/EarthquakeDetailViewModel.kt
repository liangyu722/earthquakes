package com.github.liangyu.earthquakes.ui.earthquakedetail

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.liangyu.earthquakes.common.Event
import com.github.liangyu.earthquakes.R
import com.github.liangyu.earthquakes.common.Result.Error
import com.github.liangyu.earthquakes.common.Result.Success
import com.github.liangyu.earthquakes.data.EarthquakeRepository
import com.github.liangyu.earthquakes.ui.common.toEarthQuake
import com.github.liangyu.earthquakes.ui.model.Earthquake
import kotlinx.coroutines.launch
import javax.inject.Inject

class EarthquakeDetailViewModel @Inject constructor(
    private val repository: EarthquakeRepository
) : ViewModel() {

    private val _earthquake = MutableLiveData<Earthquake>()
    val earthquake: LiveData<Earthquake> = _earthquake

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarMessage: LiveData<Event<Int>> = _snackbarText

    fun start(eqid: String) {
        // Show loading indicator
        _dataLoading.value = true
        viewModelScope.launch {
            repository.getEarthquake(eqid, false).let { result ->
                when (result) {
                    is Success -> onTaskLoaded(result.data.toEarthQuake())
                    is Error -> onDataNotAvailable(result)
                }
            }
            _dataLoading.value = false
        }
    }

    private fun onTaskLoaded(earthquake: Earthquake) {
        _earthquake.value = earthquake
    }

    private fun onDataNotAvailable(result: Error) {
        _earthquake.value = null
        showSnackbarMessage(R.string.loading_earthquake_error)
    }

    private fun showSnackbarMessage(@StringRes message: Int) {
        _snackbarText.value = Event(message)
    }
}
