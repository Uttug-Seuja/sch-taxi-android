package com.sch.data.model.remote.response


data class NotificationResponse(
    val content: String,
    val created_date: String,
    val image_url: String,
    val notification_id: Int,
    val send_user_id: Int,
    val title: String,
    var isExpanded: Boolean = false,
    var isChecked: Boolean = false
)