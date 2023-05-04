package com.sch.data.model.remote.response

import com.google.gson.annotations.SerializedName

data class MyReservationResponse(
    @SerializedName("reservation") val reservation: List<ReservationResponse>,

    )
