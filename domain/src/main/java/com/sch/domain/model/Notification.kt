package com.sch.domain.model


data class Notification(
    val notificationId: Int,
    val title: String,
    val content: String,
    val createdDate: String
)