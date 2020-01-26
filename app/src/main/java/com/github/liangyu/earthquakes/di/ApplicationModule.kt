package com.github.liangyu.earthquakes.di

import android.app.Application
import com.github.liangyu.earthquakes.data.DefaultEarthquakeRepository
import com.github.liangyu.earthquakes.data.EarthquakeRepository
import com.github.liangyu.earthquakes.data.networking.GeoNameEarthquakeService
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    fun providesEarthquakeRepository(
        geoNameEarthquakeService: GeoNameEarthquakeService
    ): EarthquakeRepository {
        return DefaultEarthquakeRepository(geoNameEarthquakeService, Dispatchers.IO)
    }
}
