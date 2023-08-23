package com.sch.domain.model


data class PagingChat(
    val chatPagingResponseDtoList: List<Chat>,
//    @SerializedName("last") val last: Boolean
)