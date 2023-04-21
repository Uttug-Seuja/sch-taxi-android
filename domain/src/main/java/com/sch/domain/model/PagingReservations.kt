package com.sch.domain.model


data class PagingReservations(
    val content: List<Reservation>,
    val last: Boolean,
)