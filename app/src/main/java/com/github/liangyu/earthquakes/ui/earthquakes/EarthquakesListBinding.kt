package com.github.liangyu.earthquakes.ui.earthquakes

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.liangyu.earthquakes.data.EarthquakeEntity
import com.github.liangyu.earthquakes.ui.earthquakes.EarthquakesAdapter

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<EarthquakeEntity>) {
    (listView.adapter as EarthquakesAdapter).submitList(items)
}
