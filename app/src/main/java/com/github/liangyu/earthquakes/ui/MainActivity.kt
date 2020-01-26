package com.github.liangyu.earthquakes.ui

import android.os.Bundle
import com.github.liangyu.earthquakes.R
import com.github.liangyu.earthquakes.data.EarthquakeRepository
import com.github.liangyu.earthquakes.data.Result
import com.github.liangyu.earthquakes.data.networking.GeoNameEarthquakeService
import com.github.liangyu.earthquakes.ui.common.BaseActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject lateinit var repository: EarthquakeRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPresentationComponent().inject(this)
        setContentView(R.layout.activity_main)


        GlobalScope.launch {
            val list = repository.getEarthquakes(false)

            (list as Result.Success).data.forEach {
                System.out.println(it)
            }
        }

    }
}
