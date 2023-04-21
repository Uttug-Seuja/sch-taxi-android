package com.sch.data.model.remote.response

data class AlarmResponse (
    val content: String,
    val created_at: String,
    val title: String,
    val type: String,
    val user_id: Int,
    val user_profile: String
)