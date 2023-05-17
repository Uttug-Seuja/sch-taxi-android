package com.sch.sch_taxi.ui.myparticipation

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sch.domain.model.Reservation
import com.sch.sch_taxi.ui.myparticipation.adapter.MyParticipationAdapter

@BindingAdapter("participationAdapter")
fun RecyclerView.bindParticipationAdapter(itemList: List<Reservation>) {
    val boundAdapter = this.adapter
    if (boundAdapter is MyParticipationAdapter) {
        boundAdapter.submitList(itemList)
    }
}