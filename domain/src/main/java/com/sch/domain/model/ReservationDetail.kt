package com.sch.domain.model

data class ReservationDetail(
    val reservationId: Int,
    val title: String,
    val startPoint: String,
    val destination: String,
    val departureDate: String,
    var reservationStatus: String,
    val gender: String,
    val passengerNum: Int,
    val currentNum: Int,
    val startLatitude: Double,
    val startLongitude: Double,
    val destinationLatitude: Double,
    val destinationLongitude: Double,
    val hostInfo: HostInfo,
    val iHost: Boolean,
    val createDate: String,
    val lastModifyDate: String
)