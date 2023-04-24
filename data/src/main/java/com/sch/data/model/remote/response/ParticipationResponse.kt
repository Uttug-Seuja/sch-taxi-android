package com.sch.data.model.remote.response

import com.google.gson.annotations.SerializedName

data class ParticipationResponse(
    @SerializedName("participationId") val participationId: Int,
)