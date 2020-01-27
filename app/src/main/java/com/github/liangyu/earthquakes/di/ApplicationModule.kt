package com.github.liangyu.earthquakes.di

import com.github.liangyu.earthquakes.data.DefaultEarthquakeRepository
import com.github.liangyu.earthquakes.data.EarthquakeRepository
import com.github.liangyu.earthquakes.data.networking.GeoNameEarthquakeService
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class ApplicationModule {

    @Provides
    fun providesEarthquakeRepository(
        geoNameEarthquakeService: GeoNameEarthquakeService
    ): EarthquakeRepository {
        return DefaultEarthquakeRepository(geoNameEarthquakeService, Dispatchers.IO)
    }
}
