package com.github.liangyu.earthquakes.ui.earthquakes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.liangyu.earthquakes.databinding.EarthquakesFragBinding
import com.github.liangyu.earthquakes.di.ViewModelFactory
import com.github.liangyu.earthquakes.ui.common.BaseFragment
import com.github.liangyu.earthquakes.util.viewModelProvider
import javax.inject.Inject

class EarthquakesFragment : BaseFragment() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: EarthquakesViewModel
    private lateinit var viewDataBinding: EarthquakesFragBinding
    private lateinit var listAdapter: EarthquakesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = viewModelProvider(viewModelFactory)
        viewDataBinding = EarthquakesFragBinding.inflate(inflater, container, false)
        viewDataBinding.viewmodel = viewModel
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListAdapter()
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            listAdapter = EarthquakesAdapter(viewModel)
            viewDataBinding.earthquakesList.adapter = listAdapter
        }
    }
}