package com.sch.data.model.remote.response

import com.google.gson.annotations.SerializedName


data class ReservationResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("reservationTime") val reservationTime: String,
    @SerializedName("startingPlace") val startingPlace: String,
    @SerializedName("destination") val destination: String,
    @SerializedName("sex") val sex: String,
    @SerializedName("reservationStatus") val reservationStatus: String,
)