package com.github.liangyu.earthquakes

import android.content.Context
import com.github.liangyu.earthquakes.data.EarthquakeRepository
import com.github.liangyu.earthquakes.di.presentation.EarthquakesModule
import com.github.liangyu.earthquakes.ui.earthquakes.EarthquakeRepositoryTD
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        MockRepositoryModule::class,
        EarthquakesModule::class
    ]
)
interface TestApplicationComponent : AndroidInjector<TestApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): TestApplicationComponent
    }

    val repository: EarthquakeRepository
}