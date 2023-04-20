package com.sch.data.model.remote.response

import com.google.gson.annotations.SerializedName

data class PostParticipationResponse(
    @SerializedName("reservationId") val reservationId: Int

)
