package com.sch.data.model.remote.request

import com.google.gson.annotations.SerializedName


data class GetReservationKeywordRequest(
    @SerializedName("keyword") val keyword: String
)