package com.sch.data.model.remote.request

import com.google.gson.annotations.SerializedName


data class GetReservationWordRequest(
    @SerializedName("word") val word: String
)