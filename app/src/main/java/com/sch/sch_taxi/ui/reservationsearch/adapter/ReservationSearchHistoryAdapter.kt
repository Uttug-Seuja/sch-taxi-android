package com.sch.sch_taxi.ui.reservationsearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sch.domain.model.SearchHistory
import com.sch.sch_taxi.databinding.HolderReservationSearchHistoryBinding
import com.sch.sch_taxi.ui.reservationsearch.ReservationSearchActionHandler

class ReservationSearchHistoryAdapter(
    private val eventListener: ReservationSearchActionHandler,
) : ListAdapter<SearchHistory, ReservationSearchHistoryAdapter.TaxiSearchHistoryViewHolder>(TaxiSearchHistoryItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaxiSearchHistoryViewHolder {
        return TaxiSearchHistoryViewHolder(
            HolderReservationSearchHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                .apply {
                    eventListener = this@ReservationSearchHistoryAdapter.eventListener
                }
        )
    }

    override fun onBindViewHolder(holder: TaxiSearchHistoryViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }

    class TaxiSearchHistoryViewHolder(
        private val binding: HolderReservationSearchHistoryBinding
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