package com.sch.sch_taxi.ui.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sch.domain.model.ChatRoom
import com.sch.domain.model.Taxi
import com.sch.sch_taxi.databinding.HolderChatBinding
import com.sch.sch_taxi.ui.chat.ChatActionHandler

class ChatAdapter(
    private val eventListener: ChatActionHandler,
) : ListAdapter<ChatRoom, ChatAdapter.ChatViewHolder>(ChatItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(
            HolderChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                .apply {
                    eventListener = this@ChatAdapter.eventListener
                }
        )
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }

    class ChatViewHolder(
        private val binding: HolderChatBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChatRoom) {
            binding.apply {
                holder = item
                executePendingBindings()
            }
        }
    }



    internal object ChatItemDiffCallback : DiffUtil.ItemCallback<ChatRoom>() {
        override fun areItemsTheSame(oldItem: ChatRoom, newItem: ChatRoom) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: ChatRoom, newItem: ChatRoom) =
            oldItem.equals(newItem)
    }
}