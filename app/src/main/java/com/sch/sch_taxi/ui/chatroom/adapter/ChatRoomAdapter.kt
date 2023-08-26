package com.sch.sch_taxi.ui.chatroom.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sch.domain.model.Chat
import com.sch.domain.model.Taxi
import com.sch.sch_taxi.databinding.HolderChatRoomMeBinding
import com.sch.sch_taxi.databinding.HolderChatRoomOtherBinding
import com.sch.sch_taxi.ui.chatroom.ChatRoomActionHandler

//class ChatRoomAdapter(
//    private val eventListener: ChatRoomActionHandler,
//) : PagingDataAdapter<Chat, RecyclerView.ViewHolder>(TaxiSearchItemDiffCallback) {
//
//    private val VIEW_TYPE_USER_MESSAGE_ME = 10
//    private val VIEW_TYPE_USER_MESSAGE_OTHER = 11
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  RecyclerView.ViewHolder {
//        Log.d("ttt", "들어옴?")
//
//        Log.d("ttt viewType", viewType.toString())
//        return when (viewType) {
//            VIEW_TYPE_USER_MESSAGE_ME -> {
//                MyUserHolder(
//                    HolderChatRoomMeBinding.inflate(
//                        LayoutInflater.from(parent.context),
//                        parent,
//                        false
//                    )
//                        .apply {
//                            eventListener = this@ChatRoomAdapter.eventListener
//                        }
//                )
//            }
//            VIEW_TYPE_USER_MESSAGE_OTHER -> {
//                OtherUserHolder(
//                    HolderChatRoomOtherBinding.inflate(
//                        LayoutInflater.from(parent.context),
//                        parent,
//                        false
//                    )
//                        .apply {
//                            eventListener = this@ChatRoomAdapter.eventListener
//                        }
//                )
//            }
//            else -> MyUserHolder(
//                HolderChatRoomMeBinding.inflate(
//                    LayoutInflater.from(parent.context),
//                    parent,
//                    false
//                )
//                    .apply {
//                        eventListener = this@ChatRoomAdapter.eventListener
//                    }
//            )
//        }
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        when (holder.itemViewType) {
//            VIEW_TYPE_USER_MESSAGE_ME -> {
//                getItem(position)?.let { item ->
//                    holder as MyUserHolder
//                    holder.bind(item)
//                }
//            }
//            VIEW_TYPE_USER_MESSAGE_OTHER -> {
//                getItem(position)?.let { item ->
//                    holder as OtherUserHolder
//                    holder.bind(item)
//                }
//            }
//        }
//    }
//
//    class MyUserHolder(
//        private val binding: HolderChatRoomMeBinding
//    ) : RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(item: Chat) {
//            binding.apply {
//                holder = item
//                executePendingBindings()
//            }
//        }
//    }
//
//    class OtherUserHolder(
//        private val binding: HolderChatRoomOtherBinding
//    ) : RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(item: Chat) {
//            binding.apply {
//                holder = item
//                executePendingBindings()
//            }
//        }
//    }
//
//
//    internal object TaxiSearchItemDiffCallback : DiffUtil.ItemCallback<Chat>() {
//        override fun areItemsTheSame(oldItem: Chat, newItem: Chat) =
//            oldItem == newItem
//
//        override fun areContentsTheSame(oldItem: Chat, newItem: Chat) =
//            oldItem.equals(newItem)
//    }
//}


import android.graphics.Color
import com.sch.domain.model.Reservation
import com.sch.sch_taxi.databinding.HolderReservationBinding
import com.sch.sch_taxi.ui.home.HomeActionHandler

class ChatRoomAdapter(
    private val eventListener: ChatRoomActionHandler,
) : PagingDataAdapter<Chat, ChatRoomAdapter.ViewHolder>(ReservationItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HolderChatRoomMeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                .apply {
                    eventListener = this@ChatRoomAdapter.eventListener
                }
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }

    class ViewHolder(
        private val binding: HolderChatRoomMeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Chat) {
            binding.apply {
                holder = item
                executePendingBindings()
            }
        }
    }


    internal object ReservationItemDiffCallback : DiffUtil.ItemCallback<Chat>() {
        override fun areItemsTheSame(oldItem: Chat, newItem: Chat) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Chat, newItem: Chat) =
            oldItem.equals(newItem)
    }
}