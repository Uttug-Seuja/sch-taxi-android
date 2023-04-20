package com.sch.data.model.remote.request

import com.google.gson.annotations.SerializedName


data class PostRegisterRequest(
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profilePath") val profilePath: String,
)