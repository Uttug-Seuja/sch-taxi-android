package com.sch.data.model.remote.response

import com.google.gson.annotations.SerializedName

data class GetPagingReservationResponse(
    @SerializedName("pagingReservations") val pagingReservations: PagingReservations
)