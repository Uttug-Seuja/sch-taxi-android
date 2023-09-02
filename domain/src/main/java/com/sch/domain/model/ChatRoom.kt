package com.sch.domain.model


data class ChatRoom(
    val reservationId: Int,
    val title: String,
    val startPoint: String,
    val destination: String,
    val reservationStatus: String,
    val departureDate: String,
    val gender: String,
    val passengerNum: Int,
    val currentNum: Int,
    val latestChat: LatestChat,
)