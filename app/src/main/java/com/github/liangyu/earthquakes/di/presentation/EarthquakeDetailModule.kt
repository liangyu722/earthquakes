package com.github.liangyu.earthquakes.di.presentation

import androidx.lifecycle.ViewModel
import com.github.liangyu.earthquakes.di.ViewModelBuilder
import com.github.liangyu.earthquakes.di.ViewModelKey
import com.github.liangyu.earthquakes.ui.earthquakedetail.EarthquakeDetailFragment
import com.github.liangyu.earthquakes.ui.earthquakedetail.EarthquakeDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class EarthquakeDetailModule {

    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun earthquakeFragment(): EarthquakeDetailFragment

    @Binds
    @IntoMap
    @ViewModelKey(EarthquakeDetailViewModel::class)
    abstract fun bindViewModel(viewmodel: EarthquakeDetailViewModel): ViewModel
}
