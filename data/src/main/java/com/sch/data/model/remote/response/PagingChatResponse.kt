package com.sch.data.model.remote.response

import com.google.gson.annotations.SerializedName
import com.sch.domain.model.HostInfo

data class PagingChatResponse(
    @SerializedName("myParticipationId") val myParticipationId: Int,
    @SerializedName("reservationId") val reservationId: Int,
    @SerializedName("passengerNum") val passengerNum: Int,
    @SerializedName("currentNum") val currentNum: Int,
    @SerializedName("hostInfo") val hostInfo: HostInfo,
    @SerializedName("chatHistoryDtoList") val chatPagingResponseDtoList: List<ChatResponse>,
    @SerializedName("ihost") val ihost: Boolean,

//    @SerializedName("last") val last: Boolean
)