package com.sch.sch_taxi.ui.reservationsearchresult

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sch.domain.model.Keyword
import com.sch.sch_taxi.ui.reservationsearchresult.adapter.ReservationSearchResultKeywordAdapter


@BindingAdapter("reservationSearchResultAdapter")
fun RecyclerView.bindReservationSearchResultAdapter(itemList: List<Keyword>) {
    val boundAdapter = this.adapter
    if (boundAdapter is ReservationSearchResultKeywordAdapter) {
        boundAdapter.submitList(itemList)
    }
}