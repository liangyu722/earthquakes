package com.github.liangyu.earthquakes.common

import android.content.res.Resources
import androidx.annotation.DimenRes
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun Resources.getFloatUsingCompat(@DimenRes resId: Int): Float {
    return ResourcesCompat.getFloat(this, resId)
}
