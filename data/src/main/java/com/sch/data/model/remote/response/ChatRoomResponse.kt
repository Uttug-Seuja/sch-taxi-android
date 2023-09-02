package com.sch.data.model.remote.response

import com.google.gson.annotations.SerializedName

data class ChatRoomResponse(

    @SerializedName("reservationId") val reservationId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("startPoint") val startPoint: String,
    @SerializedName("destination") val destination: String,
    @SerializedName("reservationStatus") val reservationStatus: String,
    @SerializedName("departureDate") val departureDate: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("passengerNum") val passengerNum: Int,
    @SerializedName("currentNum") val currentNum: Int,
    @SerializedName("latestChat") val latestChat: LatestChatResponse,

    )