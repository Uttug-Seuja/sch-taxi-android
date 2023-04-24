package com.sch.data.model.remote.response

import com.google.gson.annotations.SerializedName

data class UserProfileResponse(
    @SerializedName("userId") val userId: Int,
    @SerializedName("schoolNum") val schoolNum: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("profilePath") val profilePath: String,
    @SerializedName("currentTemperature") val currentTemperature: Double
    )