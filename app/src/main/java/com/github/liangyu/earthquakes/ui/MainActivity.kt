package com.github.liangyu.earthquakes.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.liangyu.earthquakes.R
import com.github.liangyu.earthquakes.data.EarthquakeRepository
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: EarthquakeRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
