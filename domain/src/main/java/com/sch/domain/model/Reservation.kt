package com.sch.domain.model


data class Reservation(
    val reservationId: Int,
    val title: String,
    val startPoint: String,
    val destination: String,
    val departureDate: String,
    val gender: String,
    val reservationStatus : String,
    val passengerNum: Int,
    val currentNum: Int,
    val startLatitude: Double,
    val startLongitude: Double,
    val destinationLatitude: Double,
    val destinationLongitude: Double,
    val iHost: Boolean,
    val hostInfo: HostInfo,
    val createAt: String,
    val updateAt: String
)