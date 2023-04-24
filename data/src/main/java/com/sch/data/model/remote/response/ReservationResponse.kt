package com.sch.data.model.remote.response

import com.google.gson.annotations.SerializedName


data class ReservationResponse(
    @SerializedName("reservationId") val reservationId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("startPoint") val startPoint: String,
    @SerializedName("destination") val destination: String,
    @SerializedName("departureDate") val departureDate: String,
    @SerializedName("reservationStatus") val reservationStatus: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("passengerNum") val passengerNum: Int,
    @SerializedName("currentNum") val currentNum: Int,
    @SerializedName("startLatitude") val startLatitude: Double,
    @SerializedName("startLongitude") val startLongitude: Double,
    @SerializedName("destinationLatitude") val destinationLatitude: Double,
    @SerializedName("destinationLongitude") val destinationLongitude: Double,
    @SerializedName("isHost") val isHost: Boolean,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updateAt") val updateAt: String,
//    @SerializedName("userInfoVo") val updateAt: String,



    )