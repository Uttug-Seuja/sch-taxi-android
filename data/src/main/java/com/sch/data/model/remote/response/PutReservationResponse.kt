package com.sch.data.model.remote.response

import com.google.gson.annotations.SerializedName

data class PutReservationResponse(
    @SerializedName("reservationId")val reservationId: Int
)