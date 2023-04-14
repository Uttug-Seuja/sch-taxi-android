package com.sch.sch_taxi.ui.chatroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sch.domain.model.Taxi
import com.sch.sch_taxi.databinding.HolderChatRoomMeBinding
import com.sch.sch_taxi.databinding.HolderChatRoomOtherBinding
import com.sch.sch_taxi.ui.chatroom.ChatRoomActionHandler

class ChatRoomAdapter(
    private val eventListener: ChatRoomActionHandler,
) : ListAdapter<Taxi, RecyclerView.ViewHolder>(TaxiSearchItemDiffCallback) {

    private val VIEW_TYPE_USER_MESSAGE_ME = 10
    private val VIEW_TYPE_USER_MESSAGE_OTHER = 11

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_USER_MESSAGE_ME -> {
                MyUserHolder(
                    HolderChatRoomMeBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                        .apply {
                            eventListener = this@ChatRoomAdapter.eventListener
                        }
                )
            }
            VIEW_TYPE_USER_MESSAGE_OTHER -> {
                OtherUserHolder(
                    HolderChatRoomOtherBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                        .apply {
                            eventListener = this@ChatRoomAdapter.eventListener
                        }
                )
            }
            else -> MyUserHolder(
                HolderChatRoomMeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                    .apply {
                        eventListener = this@ChatRoomAdapter.eventListener
                    }
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_USER_MESSAGE_ME -> {
                getItem(position)?.let { item ->
                    holder as MyUserHolder
                    holder.bind(item)
                }
            }
            VIEW_TYPE_USER_MESSAGE_OTHER -> {
                getItem(position)?.let { item ->
                    holder as OtherUserHolder
                    holder.bind(item)
                }
            }
        }
    }

    class MyUserHolder(
        private val binding: HolderChatRoomMeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Taxi) {
            binding.apply {
                holder = item
                executePendingBindings()
            }
        }
    }

    class OtherUserHolder(
        private val binding: HolderChatRoomOtherBinding
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