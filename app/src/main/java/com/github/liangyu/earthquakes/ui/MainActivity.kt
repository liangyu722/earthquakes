package com.github.liangyu.earthquakes.ui

import android.os.Bundle
import com.github.liangyu.earthquakes.R
import com.github.liangyu.earthquakes.data.networking.GeoNameEarthquakeService
import com.github.liangyu.earthquakes.ui.common.BaseActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject lateinit var earthquakeService : GeoNameEarthquakeService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPresentationComponent().inject(this)
        setContentView(R.layout.activity_main)


        GlobalScope.launch {
            val list = earthquakeService.earthquakeAsync().await().earthquakes

            list.forEach {
                System.out.println(it)
            }
        }

    }
}
