package com.sch.sch_taxi.ui.taxisearchresult.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sch.domain.model.Taxi
import com.sch.sch_taxi.databinding.HolderTaxiSearchBinding
import com.sch.sch_taxi.ui.home.HomeActionHandler
import com.sch.sch_taxi.ui.taxisearch.TaxiSearchActionHandler

class TaxiSearchResultAdapter(
    private val eventListener: TaxiSearchActionHandler,
) : ListAdapter<Taxi, TaxiSearchResultAdapter.TaxiSearchViewHolder>(TaxiSearchItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaxiSearchViewHolder {
        return TaxiSearchViewHolder(
            HolderTaxiSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                .apply {
                    eventListener = this@TaxiSearchResultAdapter.eventListener
                }
        )
    }

    override fun onBindViewHolder(holder: TaxiSearchViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }

    class TaxiSearchViewHolder(
        private val binding: HolderTaxiSearchBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Taxi) {
            binding.apply {
                holder = item
                executePendingBindings()
            }
        }
    }



    internal object TaxiSearchItemDiffCallback : DiffUtil.ItemCallback<Taxi>() {
        override fun areItemsTheSame(oldItem: Taxi, newItem: Taxi) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Taxi, newItem: Taxi) =
            oldItem.equals(newItem)
    }
}