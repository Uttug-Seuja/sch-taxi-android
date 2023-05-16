package com.sch.sch_taxi.ui.myreservation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sch.domain.model.Reservation
import com.sch.sch_taxi.databinding.HolderReservationBinding
import com.sch.sch_taxi.ui.myreservation.MyReservationActionHandler

class MyReservationAdapter(
    private val eventListener: MyReservationActionHandler,
) : ListAdapter<Reservation, MyReservationAdapter.ViewHolder>(TaxiSearchItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HolderReservationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                .apply {
//                    eventListener = this@MyPostAdapter.eventListener
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



    internal object TaxiSearchItemDiffCallback : DiffUtil.ItemCallback<Reservation>() {
        override fun areItemsTheSame(oldItem: Reservation, newItem: Reservation) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Reservation, newItem: Reservation) =
            oldItem.equals(newItem)
    }
}