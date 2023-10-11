package com.sch.sch_taxi.ui.notifications.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sch.domain.model.Alarm
import com.sch.domain.model.Notification
import com.sch.sch_taxi.databinding.HolderNotificationAlarmBinding
import com.sch.sch_taxi.ui.notifications.NotificationsActionHandler

class NotificationsAdapter(
    private val eventListener: NotificationsActionHandler,
) : PagingDataAdapter<Notification, NotificationsAdapter.NotificationsViewHolder>(TaxiSearchItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationsViewHolder {
        return NotificationsViewHolder(
            HolderNotificationAlarmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                .apply {
                    eventListener = this@NotificationsAdapter.eventListener
                }
        )
    }

    override fun onBindViewHolder(holder: NotificationsViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }

    class NotificationsViewHolder(
        private val binding: HolderNotificationAlarmBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Notification) {
            binding.apply {
                holder = item
                executePendingBindings()
            }
        }
    }



    internal object TaxiSearchItemDiffCallback : DiffUtil.ItemCallback<Notification>() {
        override fun areItemsTheSame(oldItem: Notification, newItem: Notification) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Notification, newItem: Notification) =
            oldItem.equals(newItem)
    }
}