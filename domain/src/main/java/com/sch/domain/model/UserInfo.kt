package com.sch.domain.model


data class UserInfo(
    val userId: Int,
    val schoolNum: String,
    val gender: String,
    val name: String,
    val email: String,
    val profilePath: String,
    val currentTemperature: Double,
)