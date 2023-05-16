package com.sch.data.model.remote.response

import com.google.gson.annotations.SerializedName

data class ParticipationResponse(
    @SerializedName("participationInfoList") val participationInfoList: List<ParticipationInfoListResponse>,
    @SerializedName("iparticipation") val iparticipation: Boolean

)