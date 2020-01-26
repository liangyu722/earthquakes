package com.github.liangyu.earthquakes

import android.app.Application
import com.github.liangyu.earthquakes.di.ApplicationComponent
import com.github.liangyu.earthquakes.di.ApplicationModule
import com.github.liangyu.earthquakes.di.DaggerApplicationComponent

class EarthquakeApplication : Application() {

    private lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    fun getApplicationComponent(): ApplicationComponent {
        return applicationComponent
    }

}
