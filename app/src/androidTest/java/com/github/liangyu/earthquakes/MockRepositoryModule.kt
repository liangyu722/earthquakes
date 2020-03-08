package com.github.liangyu.earthquakes

import com.github.liangyu.earthquakes.data.EarthquakeRepository
import com.github.liangyu.earthquakes.ui.earthquakes.EarthquakeRepositoryTD
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MockRepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(): EarthquakeRepository =
        EarthquakeRepositoryTD()
}