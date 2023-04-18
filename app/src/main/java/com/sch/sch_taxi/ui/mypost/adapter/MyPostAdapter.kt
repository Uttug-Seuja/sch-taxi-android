package com.sch.sch_taxi.ui.mypost.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sch.domain.model.Taxi
import com.sch.sch_taxi.databinding.HolderNotificationBinding
import com.sch.sch_taxi.ui.mypost.MyPostActionHandler

class MyPostAdapter(
    private val eventListener: MyPostActionHandler,
) : ListAdapter<Taxi, MyPostAdapter.NotificationsViewHolder>(TaxiSearchItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationsViewHolder {
        return NotificationsViewHolder(
            HolderNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                .apply {
                    eventListener = this@MyPostAdapter.eventListener
                }
        )
    }

    override fun onBindViewHolder(holder: NotificationsViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }

    class NotificationsViewHolder(
        private val binding: HolderNotificationBinding
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