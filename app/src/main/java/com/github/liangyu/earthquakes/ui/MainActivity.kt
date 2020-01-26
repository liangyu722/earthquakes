package com.github.liangyu.earthquakes.ui

import android.os.Bundle
import com.github.liangyu.earthquakes.R
import com.github.liangyu.earthquakes.ui.common.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPresentationComponent().inject(this)
        setContentView(R.layout.activity_main)
    }
}
