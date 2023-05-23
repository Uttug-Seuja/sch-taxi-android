package com.sch.sch_taxi.ui.reservationsearchresult.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sch.domain.model.Keyword
import com.sch.sch_taxi.databinding.HolderReservationResultSearchBinding
import com.sch.sch_taxi.databinding.HolderReservationSearchBinding
import com.sch.sch_taxi.ui.reservationsearchresult.ReservationSearchResultActionHandler

class ReservationSearchResultKeywordAdapter(
    private val eventListener: ReservationSearchResultActionHandler,
) : PagingDataAdapter<Keyword, ReservationSearchResultKeywordAdapter.ViewHolder>(ReservationSearchItemDiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HolderReservationResultSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                .apply {
                    eventListener = this@ReservationSearchResultKeywordAdapter.eventListener
                }
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }

    class ViewHolder(
        private val binding: HolderReservationResultSearchBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Keyword) {
            binding.apply {
                holder = item
                executePendingBindings()
            }
        }
    }



    internal object ReservationSearchItemDiffCallback : DiffUtil.ItemCallback<Keyword>() {
        override fun areItemsTheSame(oldItem: Keyword, newItem: Keyword) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Keyword, newItem: Keyword) =
            oldItem.equals(newItem)
    }
}