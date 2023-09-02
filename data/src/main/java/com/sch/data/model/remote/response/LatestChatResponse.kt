package com.sch.data.model.remote.response

import com.google.gson.annotations.SerializedName

data class LatestChatResponse(
    @SerializedName("reservationId") val reservationId: Int?,
    @SerializedName("userId") val userId: Int?,
    @SerializedName("participationId") val participationId: Int?,
    @SerializedName("writer") val writer: String?,
    @SerializedName("message") val message: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("profilePath") val profilePath: String?,
)
