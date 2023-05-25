package com.sch.sch_taxi.ui.reservationsearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sch.domain.model.Keyword
import com.sch.domain.model.Reservation
import com.sch.sch_taxi.databinding.HolderMyReservationBinding
import com.sch.sch_taxi.databinding.HolderReservationSearchBinding
import com.sch.sch_taxi.ui.myreservation.MyReservationActionHandler
import com.sch.sch_taxi.ui.reservationsearch.ReservationSearchActionHandler

class ReservationKeywordAdapter(
    private val eventListener: ReservationSearchActionHandler,
) : ListAdapter<Keyword, ReservationKeywordAdapter.ViewHolder>(TaxiSearchItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HolderReservationSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                .apply {
                    eventListener = this@ReservationKeywordAdapter.eventListener
                }
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }

    class ViewHolder(
        private val binding: HolderReservationSearchBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Keyword) {
            binding.apply {
                holder = item
                executePendingBindings()
            }
        }
    }



    internal object TaxiSearchItemDiffCallback : DiffUtil.ItemCallback<Keyword>() {
        override fun areItemsTheSame(oldItem: Keyword, newItem: Keyword) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Keyword, newItem: Keyword) =
            oldItem.equals(newItem)
    }
}
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.paging.PagingDataAdapter
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.RecyclerView
//import com.sch.domain.model.Keyword
//import com.sch.sch_taxi.databinding.HolderReservationSearchBinding
//import com.sch.sch_taxi.ui.reservationsearch.ReservationSearchActionHandler
//
//class ReservationKeywordAdapter(
//    private val eventListener: ReservationSearchActionHandler,
//) : PagingDataAdapter<Keyword, ReservationKeywordAdapter.ViewHolder>(ReservationSearchItemDiffCallback){
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(
//            HolderReservationSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//                .apply {
//                    eventListener = this@ReservationKeywordAdapter.eventListener
//                }
//        )
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        getItem(position)?.let { item ->
//            holder.bind(item)
//        }
//    }
//
//    class ViewHolder(
//        private val binding: HolderReservationSearchBinding
//    ) : RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(item: Keyword) {
//            binding.apply {
//                holder = item
//                executePendingBindings()
//            }
//        }
//    }
//
//
//
//    internal object ReservationSearchItemDiffCallback : DiffUtil.ItemCallback<Keyword>() {
//        override fun areItemsTheSame(oldItem: Keyword, newItem: Keyword) =
//            oldItem == newItem
//
//        override fun areContentsTheSame(oldItem: Keyword, newItem: Keyword) =
//            oldItem.equals(newItem)
//    }
//}