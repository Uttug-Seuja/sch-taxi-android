package com.sch.data.model.remote.response

import com.google.gson.annotations.SerializedName
import com.sch.domain.model.Notification

data class PagingReservations(
    @SerializedName("contnet") val content: List<ReservationResponse>,
    @SerializedName("last") val last: Boolean,
)