package com.sch.sch_taxi.ui.reservationupdate.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sch.domain.model.KakaoLocal
import com.sch.sch_taxi.databinding.HolderKakaoLocalUpdateBinding
import com.sch.sch_taxi.ui.reservationupdate.ReservationUpdateActionHandler

class KakaoLocalUpdateAdapter(
    private val eventListener: ReservationUpdateActionHandler,
) : ListAdapter<KakaoLocal, KakaoLocalUpdateAdapter.TaxiCreateViewHolder>(KakaoLocalItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaxiCreateViewHolder {
        return TaxiCreateViewHolder(
            HolderKakaoLocalUpdateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                .apply {
                    eventListener = this@KakaoLocalUpdateAdapter.eventListener
                }
        )
    }

    override fun onBindViewHolder(holder: TaxiCreateViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }

    class TaxiCreateViewHolder(
        private val binding: HolderKakaoLocalUpdateBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: KakaoLocal) {
            binding.apply {
                holder = item
                executePendingBindings()
            }
        }
    }



    internal object KakaoLocalItemDiffCallback : DiffUtil.ItemCallback<KakaoLocal>() {
        override fun areItemsTheSame(oldItem: KakaoLocal, newItem: KakaoLocal) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: KakaoLocal, newItem: KakaoLocal) =
            oldItem.equals(newItem)
    }
}