package com.github.liangyu.earthquakes.ui.earthquakes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.liangyu.earthquakes.EventObserver
import com.github.liangyu.earthquakes.databinding.EarthquakesFragBinding
import com.github.liangyu.earthquakes.util.viewModelProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class EarthquakesFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
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
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupListAdapter()
        setupNavigation()
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            listAdapter = EarthquakesAdapter(viewModel)
            viewDataBinding.earthquakesList.adapter = listAdapter
        }
    }

    private fun setupNavigation() {
        viewModel.openEarthquakeEvent.observe(this, EventObserver {
            openEarthquakeDetails(it)
        })
    }

    private fun openEarthquakeDetails(eqid: String) {
        val action = EarthquakesFragmentDirections
            .actionEarthquakesFragmentToEarthquakeDetailFragment(eqid)
        findNavController().navigate(action)
    }
}