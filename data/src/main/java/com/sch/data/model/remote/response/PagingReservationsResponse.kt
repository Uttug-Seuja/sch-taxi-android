package com.sch.data.model.remote.response

import com.google.gson.annotations.SerializedName
import com.sch.domain.model.Reservation

data class PagingReservationsResponse(
    @SerializedName("content") val content: List<Reservation>,
    @SerializedName("last") val last: Boolean,
)