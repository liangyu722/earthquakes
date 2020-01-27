package com.github.liangyu.earthquakes.di.presentation

import com.github.liangyu.earthquakes.ui.MainActivity
import dagger.Subcomponent

@Subcomponent(modules = [PresentationModule::class, ViewModelModule::class])
interface PresentationComponent {
    fun inject(mainActivity: MainActivity)
}
