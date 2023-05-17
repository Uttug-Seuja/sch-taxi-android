package com.sch.sch_taxi.ui.myreservation


import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sch.domain.model.Reservation
import com.sch.sch_taxi.ui.myreservation.adapter.MyReservationAdapter

@BindingAdapter("reservationAdapter")
fun RecyclerView.bindReservationAdapter(itemList: List<Reservation>) {
    val boundAdapter = this.adapter
    if (boundAdapter is MyReservationAdapter) {
        boundAdapter.submitList(itemList)
    }
}