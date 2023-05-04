package com.sch.domain.model


data class PagingReservation(
    val content: List<Reservation>,
    val last: Boolean,
)