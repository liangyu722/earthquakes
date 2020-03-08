package com.github.liangyu.earthquakes.ui.earthquakedetail

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.liangyu.earthquakes.R
import com.github.liangyu.earthquakes.common.Event
import com.github.liangyu.earthquakes.common.Result.Error
import com.github.liangyu.earthquakes.common.Result.Success
import com.github.liangyu.earthquakes.data.EarthquakeRepository
import com.github.liangyu.earthquakes.ui.common.toEarthQuake
import com.github.liangyu.earthquakes.ui.model.Earthquake
import kotlinx.coroutines.launch
import javax.inject.Inject

class EarthquakeDetailViewModel @Inject constructor(
    private val repository: EarthquakeRepository
) : ViewModel(), ViewEarthquakeDetailViewModel {

    override val earthquake = MutableLiveData<Earthquake>()

    override val dataLoading = MutableLiveData<Boolean>()

    override val snackbarMessage = MutableLiveData<Event<Int>>()

    override fun start(eqid: String) {
        // Show loading indicator
        dataLoading.value = true
        viewModelScope.launch {
            repository.getEarthquake(eqid, false).let { result ->
                when (result) {
                    is Success -> onTaskLoaded(result.data.toEarthQuake())
                    is Error -> onDataNotAvailable(result)
                }
            }
            dataLoading.value = false
        }
    }

    private fun onTaskLoaded(earthquake: Earthquake) {
        this.earthquake.value = earthquake
    }

    private fun onDataNotAvailable(result: Error) {
        earthquake.value = null
        showSnackbarMessage(R.string.loading_earthquake_error)
    }

    private fun showSnackbarMessage(@StringRes message: Int) {
        snackbarMessage.value = Event(message)
    }
}
