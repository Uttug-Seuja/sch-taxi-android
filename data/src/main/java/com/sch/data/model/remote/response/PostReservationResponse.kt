package com.sch.data.model.remote.response

import com.google.gson.annotations.SerializedName

data class PostReservationResponse(
    @SerializedName("reservationId")val reservationId: Int
)