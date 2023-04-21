package com.sch.data.model.remote.response

import com.google.gson.annotations.SerializedName

data class UserProfileResponse(
    @SerializedName("email") val email: String,
    @SerializedName("id") val id: Int,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profilePath") val profilePath: String
)