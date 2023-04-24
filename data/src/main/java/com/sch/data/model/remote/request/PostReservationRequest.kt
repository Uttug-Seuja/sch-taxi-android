package com.sch.data.model.remote.request

import com.google.gson.annotations.SerializedName


data class PostReservationRequest(
    @SerializedName("title") val title: String,
    @SerializedName("startPoint") val startPoint: String,
    @SerializedName("destination") val destination: String,
    @SerializedName("departureDate") val departureDate: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("startLatitude") val startLatitude: Double,
    @SerializedName("startLongitude") val startLongitude: Double,
    @SerializedName("destinationLatitude") val destinationLatitude: Double,
    @SerializedName("destinationLongitude") val destinationLongitude: Double,
)