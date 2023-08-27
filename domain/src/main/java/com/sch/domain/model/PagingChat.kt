package com.sch.domain.model


data class PagingChat(
    val chatPagingResponseDtoList: List<Chat>,
    val reservationId: Int,
    val passengerNum: Int,
    val currentNum: Int,
    val hostInfo: HostInfo,
//  l last: Boolean
)

