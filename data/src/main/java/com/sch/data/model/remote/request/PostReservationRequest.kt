package com.sch.data.model.remote.request

import com.google.gson.annotations.SerializedName


data class PostReservationRequest(
    @SerializedName("title") val title: String,
    @SerializedName("reserveDate") val reserveDate: String,
    @SerializedName("sex") val sex: String,
    @SerializedName("startingPlace") val startingPlace: String,
    @SerializedName("destination") val destination: String,
    @SerializedName("challengeWord") val challengeWord: String,
    @SerializedName("countersignWord") val countersignWord: String,
    @SerializedName("startLatitude") val startLatitude: Double,
    @SerializedName("startLongitude") val startLongitude: Double,
    @SerializedName("finishLatitude") val finishLatitude: Double,
    @SerializedName("startLatitude") val finishLongitude: Double,

    )