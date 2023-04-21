package com.sch.data.model.remote.response

import com.google.gson.annotations.SerializedName

data class ParticipationResponse(
    @SerializedName("reservationId") val reservationId: Int,
    @SerializedName("isParticipation") val isParticipation: Boolean
)