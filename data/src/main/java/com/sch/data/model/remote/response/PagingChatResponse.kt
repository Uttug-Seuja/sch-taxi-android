package com.sch.data.model.remote.response

import com.google.gson.annotations.SerializedName

data class PagingChatResponse(
    @SerializedName("chatPagingResponseDtoList") val chatPagingResponseDtoList: List<ChatResponse>,
//    @SerializedName("last") val last: Boolean
)