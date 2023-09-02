package com.sch.domain.model


data class LatestChat(
    val reservationId: Int?,
    val userId: Int?,
    val participationId: Int?,
    val writer: String?,
    val message: String,
    val createdAt: String,
    val profilePath: String?,
)
