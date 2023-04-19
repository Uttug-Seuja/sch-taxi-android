package com.sch.data.model.remote.request

import com.google.gson.annotations.SerializedName


data class PostRefreshTokenRequest(
    @SerializedName("refresh_token") val refresh_token: String
)