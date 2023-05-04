package com.sch.data.model.remote.response

import com.google.gson.annotations.SerializedName


data class ParticipationInfoListResponse(
    @SerializedName("seatPosition") val seatPosition: String,
    @SerializedName("userInfo") val userInfo: UserInfoResponse,
    @SerializedName("iparticipation") val iparticipation: Boolean
)