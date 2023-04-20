package com.sch.data.model.remote.request

import com.google.gson.annotations.SerializedName


data class PostRegisterRequest(
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profile_path") val profile_path: String,
    )