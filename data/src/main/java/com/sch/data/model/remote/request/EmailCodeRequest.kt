package com.sch.data.model.remote.request

data class EmailCodeRequest(
    val email: String,
    val code: String,
    val oauthProvider: String,
)