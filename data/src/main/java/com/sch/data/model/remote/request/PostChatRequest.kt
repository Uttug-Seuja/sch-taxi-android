package com.sch.data.model.remote.request

data class PostChatRequest(
    val message: String,
    val writer: String,
    val cursor: String,
    val userId: Int,
    val participationId : Int
)