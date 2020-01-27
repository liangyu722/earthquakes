package com.github.liangyu.earthquakes.di.presentation

import androidx.lifecycle.ViewModel
import com.github.liangyu.earthquakes.di.ViewModelBuilder
import com.github.liangyu.earthquakes.di.ViewModelKey
import com.github.liangyu.earthquakes.ui.earthquakes.EarthquakesFragment
import com.github.liangyu.earthquakes.ui.earthquakes.EarthquakesViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class EarthquakesModule {

    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun earthquakeFragment(): EarthquakesFragment

    @Binds
    @IntoMap
    @ViewModelKey(EarthquakesViewModel::class)
    abstract fun bindViewModel(viewmodel: EarthquakesViewModel): ViewModel
}
