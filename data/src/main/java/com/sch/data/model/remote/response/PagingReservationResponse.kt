package com.sch.data.model.remote.response

import com.google.gson.annotations.SerializedName

data class PagingReservationResponse(
    @SerializedName("pagingReservations") val pagingReservationsResponse: PagingReservationsResponse
)