package com.sch.data.model.remote.response

import com.google.gson.annotations.SerializedName

data class ReservationKeywordResponse(
    @SerializedName("keywordId") val keywordId: Int,
    @SerializedName("keywordTitle") val keywordTitle: String,
)