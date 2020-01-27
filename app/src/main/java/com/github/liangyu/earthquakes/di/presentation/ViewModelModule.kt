package com.github.liangyu.earthquakes.di.presentation

import androidx.lifecycle.ViewModel
import com.github.liangyu.earthquakes.data.EarthquakeRepository
import com.github.liangyu.earthquakes.di.ViewModelFactory
import com.github.liangyu.earthquakes.ui.earthquakes.EarthquakesViewModel
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider
import kotlin.reflect.KClass

@Module
class ViewModelModule {

    @kotlin.annotation.Target(
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER
    )
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    @MapKey
    annotation class ViewModelKey(val value: KClass<out ViewModel>)

    @Provides
    internal fun providesViewModelFactory(
        providerMap: Map<Class<out ViewModel>, Provider<ViewModel>>
    ): ViewModelFactory {
        return ViewModelFactory(providerMap)
    }

    @Provides
    @IntoMap
    @ViewModelKey(EarthquakesViewModel::class)
    internal fun providesEarthquakesViewModel(repository: EarthquakeRepository): ViewModel {
        return EarthquakesViewModel(repository)
    }

}
