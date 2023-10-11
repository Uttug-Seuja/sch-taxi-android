package com.sch.data.model.remote.response


data class NotificationResponse(
    val notificationId: Int,
    val title: String,
    val content: String,
    val createdDate: String
)