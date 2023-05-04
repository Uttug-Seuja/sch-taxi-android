package com.sch.data.model.remote.response

import com.google.gson.annotations.SerializedName

data class PagingReservationKeywordResponse(
    @SerializedName("content") val content: List<String>,
    @SerializedName("last") val last: Boolean
)