package com.sch.data.model.remote.request

import com.google.gson.annotations.SerializedName


data class PutReservationRequest(
    @SerializedName("title") val title: String
)