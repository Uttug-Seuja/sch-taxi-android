package com.sch.domain.model


data class UserProfile(
    val email: String,
    val id: Int,
    val nickname: String,
    val profilePath: String
)