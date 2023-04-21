package com.sch.domain.model



data class Reservation(
    val id: Int,
    val title: String,
    val reservationTime: String,
    val startingPlace: String,
    val destination: String,
    val sex: String,
    val reservationStatus: String,
)