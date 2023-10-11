package com.sch.data.model.remote.response


data class NotificationListResponse(
    val size: Int,
    val content: List<NotificationResponse>,
    val number: Int,
    val numberOfElements: Int,
    val first: Boolean,
    val last: Boolean,
    val empty: Boolean
)