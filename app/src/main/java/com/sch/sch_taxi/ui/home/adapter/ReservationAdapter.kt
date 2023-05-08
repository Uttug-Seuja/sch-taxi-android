package com.sch.sch_taxi.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sch.domain.model.Reservation
import com.sch.sch_taxi.databinding.HolderReservationBinding
import com.sch.sch_taxi.ui.home.HomeActionHandler

class ReservationAdapter(
    private val eventListener: HomeActionHandler,
) : PagingDataAdapter<Reservation, ReservationAdapter.ViewHolder>(ReservationItemDiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HolderReservationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                .apply {
                    eventListener = this@ReservationAdapter.eventListener
                }
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }

    class ViewHolder(
        private val binding: HolderReservationBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Reservation) {
            binding.apply {
                holder = item
                executePendingBindings()
            }
        }
    }



    internal object ReservationItemDiffCallback : DiffUtil.ItemCallback<Reservation>() {
        override fun areItemsTheSame(oldItem: Reservation, newItem: Reservation) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Reservation, newItem: Reservation) =
            oldItem.equals(newItem)
    }
}