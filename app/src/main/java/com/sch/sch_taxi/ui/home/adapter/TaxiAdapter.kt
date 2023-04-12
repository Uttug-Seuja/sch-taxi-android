package com.sch.sch_taxi.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sch.domain.model.Taxi
import com.sch.sch_taxi.databinding.HolderTaxiBinding
import com.sch.sch_taxi.ui.home.HomeActionHandler

class TaxiAdapter(
    private val eventListener: HomeActionHandler,
) : ListAdapter<Taxi, TaxiAdapter.TaxiViewHolder>(TaxiItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaxiViewHolder {
        return TaxiViewHolder(
            HolderTaxiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                .apply {
                    eventListener = this@TaxiAdapter.eventListener
                }
        )
    }

    override fun onBindViewHolder(holder: TaxiViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }

    class TaxiViewHolder(
        private val binding: HolderTaxiBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Taxi) {
            binding.apply {
                holder = item
                executePendingBindings()
            }
        }
    }



    internal object TaxiItemDiffCallback : DiffUtil.ItemCallback<Taxi>() {
        override fun areItemsTheSame(oldItem: Taxi, newItem: Taxi) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Taxi, newItem: Taxi) =
            oldItem.equals(newItem)
    }
}