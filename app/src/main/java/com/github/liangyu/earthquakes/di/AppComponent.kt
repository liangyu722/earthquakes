package com.github.liangyu.earthquakes.di

import com.github.liangyu.earthquakes.di.networking.ServicesModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ServicesModule::class])
interface AppComponent {

}