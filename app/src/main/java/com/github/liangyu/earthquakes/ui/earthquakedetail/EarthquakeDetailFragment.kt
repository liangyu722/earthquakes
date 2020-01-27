package com.github.liangyu.earthquakes.ui.earthquakedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.liangyu.earthquakes.R

class EarthquakeDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.earthquakedetail_frag, container, false)

        setHasOptionsMenu(true)
        return view
    }
}