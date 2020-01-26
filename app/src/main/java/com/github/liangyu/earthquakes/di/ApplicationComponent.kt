package com.github.liangyu.earthquakes.di

import com.github.liangyu.earthquakes.di.networking.ServicesModule
import com.github.liangyu.earthquakes.di.presentation.PresentationComponent
import com.github.liangyu.earthquakes.di.presentation.PresentationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ServicesModule::class])
interface ApplicationComponent {

    fun newPresentationComponent(presentationModule: PresentationModule): PresentationComponent
}