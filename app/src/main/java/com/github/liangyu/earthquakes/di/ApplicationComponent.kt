package com.github.liangyu.earthquakes.di

import android.content.Context
import com.github.liangyu.earthquakes.EarthquakeApplication
import com.github.liangyu.earthquakes.di.networking.ServiceModule
import com.github.liangyu.earthquakes.di.presentation.EarthquakeDetailModule
import com.github.liangyu.earthquakes.di.presentation.EarthquakesModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        AndroidSupportInjectionModule::class,
        ServiceModule::class,
        EarthquakesModule::class,
        EarthquakeDetailModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<EarthquakeApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}
