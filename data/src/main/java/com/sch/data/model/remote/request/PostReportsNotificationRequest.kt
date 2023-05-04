package com.sch.data.model.remote.request

import com.google.gson.annotations.SerializedName


data class PostReportsNotificationRequest(
    @SerializedName("reportReason") val reportReason: String,
    @SerializedName("reportType") val reportType: String
)