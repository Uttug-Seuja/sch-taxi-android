package com.sch.data.model.remote.response

import com.google.gson.annotations.SerializedName

data class GetParticipationResponse(
    @SerializedName("isParticipation") val isParticipation: Boolean
)