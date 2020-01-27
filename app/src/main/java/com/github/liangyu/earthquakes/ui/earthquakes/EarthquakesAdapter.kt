package com.github.liangyu.earthquakes.ui.earthquakes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.liangyu.earthquakes.data.EarthquakeEntity
import com.github.liangyu.earthquakes.databinding.EarthquakeItemBinding
import com.github.liangyu.earthquakes.ui.earthquakes.EarthquakesAdapter.ViewHolder
import com.github.liangyu.earthquakes.ui.model.Earthquake

class EarthquakesAdapter(private val viewModel: EarthquakesViewModel) :
    ListAdapter<Earthquake, ViewHolder>(EarthquakeDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(viewModel, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: EarthquakeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: EarthquakesViewModel, item: Earthquake) {
            binding.viewmodel = viewModel
            binding.earthquake = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = EarthquakeItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class EarthquakeDiffCallback : DiffUtil.ItemCallback<Earthquake>() {
    override fun areItemsTheSame(oldItem: Earthquake, newItem: Earthquake): Boolean {
        return oldItem.eqid == newItem.eqid
    }

    override fun areContentsTheSame(oldItem: Earthquake, newItem: Earthquake): Boolean {
        return oldItem == newItem
    }
}