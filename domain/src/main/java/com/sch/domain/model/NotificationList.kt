package com.sch.domain.model


data class NotificationList(
    val size: Int,
    val content: List<Notification>,
    val number: Int,
    val numberOfElements: Int,
    val first: Boolean,
    val last: Boolean,
    val empty: Boolean
)