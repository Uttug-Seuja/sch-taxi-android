package com.sch.data.model.remote.request

import com.google.gson.annotations.SerializedName


data class PostRefreshTokenRequest(
    @SerializedName("refreshToken") val refreshToken: String
)