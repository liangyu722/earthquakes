package com.github.liangyu.earthquakes.ui.earthquakes

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.liangyu.earthquakes.ui.model.Earthquake

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Earthquake>) {
    (listView.adapter as EarthquakesAdapter).submitList(items)
}
