package com.github.liangyu.earthquakes.ui.common

import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import com.github.liangyu.earthquakes.EarthquakeApplication
import com.github.liangyu.earthquakes.di.ApplicationComponent
import com.github.liangyu.earthquakes.di.presentation.PresentationComponent
import com.github.liangyu.earthquakes.di.presentation.PresentationModule

open class BaseActivity : AppCompatActivity() {
    private var mIsInjectorUsed: Boolean = false

    @UiThread
    protected fun getPresentationComponent(): PresentationComponent {
        if (mIsInjectorUsed) {
            throw RuntimeException("there is no need to use injector more than once")
        }
        mIsInjectorUsed = true
        return getApplicationComponent()
            .newPresentationComponent(PresentationModule())

    }

    private fun getApplicationComponent(): ApplicationComponent {
        return (application as EarthquakeApplication).getApplicationComponent()
    }
}