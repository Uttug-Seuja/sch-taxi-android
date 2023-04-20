package com.sch.domain.model

import com.google.gson.annotations.SerializedName

data class UserProfile(
    @SerializedName("email") val email: String,
    @SerializedName("id") val id: Int,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profilePath") val profilePath: String
)