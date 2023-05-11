package com.sch.sch_taxi.ui.reservationsearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sch.domain.model.Keyword
import com.sch.sch_taxi.databinding.HolderRecommendKeywordBinding
import com.sch.sch_taxi.ui.reservationsearch.ReservationSearchActionHandler

class RecommendKeywordAdapter(
    private val eventListener: ReservationSearchActionHandler,
) : ListAdapter<Keyword, RecommendKeywordAdapter.ViewHolder>(RecommendKeywordItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HolderRecommendKeywordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                .apply {
                    eventListener = this@RecommendKeywordAdapter.eventListener
                }
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }

    class ViewHolder(
        private val binding: HolderRecommendKeywordBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Keyword) {
            binding.apply {
                holder = item
                executePendingBindings()
            }
        }
    }



    internal object RecommendKeywordItemDiffCallback : DiffUtil.ItemCallback<Keyword>() {
        override fun areItemsTheSame(oldItem: Keyword, newItem: Keyword) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Keyword, newItem: Keyword) =
            oldItem.equals(newItem)
    }
}