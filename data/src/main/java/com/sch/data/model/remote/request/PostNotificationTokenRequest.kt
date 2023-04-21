package com.sch.data.model.remote.request

import com.google.gson.annotations.SerializedName


data class PostNotificationTokenRequest(
    @SerializedName("deviceId") val deviceId: String,
    @SerializedName("token") val token: String
)