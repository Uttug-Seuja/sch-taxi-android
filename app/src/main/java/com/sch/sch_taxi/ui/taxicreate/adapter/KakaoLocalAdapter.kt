package com.sch.sch_taxi.ui.taxicreate.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sch.domain.model.KakaoLocal
import com.sch.sch_taxi.databinding.HolderKakaoLocalBinding
import com.sch.sch_taxi.ui.taxicreate.TaxiCreateActionHandler

class KakaoLocalAdapter(
    private val eventListener: TaxiCreateActionHandler,
) : ListAdapter<KakaoLocal, KakaoLocalAdapter.TaxiCreateViewHolder>(KakaoLocalItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaxiCreateViewHolder {
        return TaxiCreateViewHolder(
            HolderKakaoLocalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                .apply {
                    eventListener = this@KakaoLocalAdapter.eventListener
                }
        )
    }

    override fun onBindViewHolder(holder: TaxiCreateViewHolder, position: Int) {
        getItem(position)?.let { item ->
            Log.d("Ttt", item.toString())
            holder.bind(item)
        }
    }

    class TaxiCreateViewHolder(
        private val binding: HolderKakaoLocalBinding
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