package com.sch.sch_taxi.ui.taxisearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sch.domain.model.SearchHistory
import com.sch.sch_taxi.databinding.HolderTaxiSearchHistoryBinding
import com.sch.sch_taxi.ui.taxisearch.TaxiSearchActionHandler

class TaxiSearchHistoryAdapter(
    private val eventListener: TaxiSearchActionHandler,
) : ListAdapter<SearchHistory, TaxiSearchHistoryAdapter.TaxiSearchHistoryViewHolder>(TaxiSearchHistoryItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaxiSearchHistoryViewHolder {
        return TaxiSearchHistoryViewHolder(
            HolderTaxiSearchHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                .apply {
                    eventListener = this@TaxiSearchHistoryAdapter.eventListener
                }
        )
    }

    override fun onBindViewHolder(holder: TaxiSearchHistoryViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }

    class TaxiSearchHistoryViewHolder(
        private val binding: HolderTaxiSearchHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SearchHistory) {
            binding.apply {
                holder = item
                executePendingBindings()
            }
        }
    }



    internal object TaxiSearchHistoryItemDiffCallback : DiffUtil.ItemCallback<SearchHistory>() {
        override fun areItemsTheSame(oldItem: SearchHistory, newItem: SearchHistory) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: SearchHistory, newItem: SearchHistory) =
            oldItem.equals(newItem)
    }
}