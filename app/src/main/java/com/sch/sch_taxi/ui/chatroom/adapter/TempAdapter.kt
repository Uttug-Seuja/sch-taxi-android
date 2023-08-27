package com.sch.sch_taxi.ui.chatroom.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sch.domain.model.Chat
import com.sch.domain.model.Reservation
import com.sch.sch_taxi.databinding.HolderChatRoomMeBinding
import com.sch.sch_taxi.databinding.HolderMyParticipationBinding
import com.sch.sch_taxi.ui.chatroom.ChatRoomActionHandler
import com.sch.sch_taxi.ui.myparticipation.MyParticipationActionHandler

class TempAdapter(
    private val eventListener: ChatRoomActionHandler,
) : ListAdapter<Chat, TempAdapter.ViewHolder>(TaxiSearchItemDiffCallback) {


    override fun getItemViewType(position: Int): Int {
        return currentList[position].userId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("ttt viewType", viewType.toString())
        return ViewHolder(
            HolderChatRoomMeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                .apply {
                    eventListener = this@TempAdapter.eventListener
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



    internal object TaxiSearchItemDiffCallback : DiffUtil.ItemCallback<Chat>() {
        override fun areItemsTheSame(oldItem: Chat, newItem: Chat) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Chat, newItem: Chat) =
            oldItem.equals(newItem)
    }
}