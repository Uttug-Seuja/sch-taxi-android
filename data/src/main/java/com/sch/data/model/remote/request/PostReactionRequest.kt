package com.sch.data.model.remote.request

import com.google.gson.annotations.SerializedName


data class PostReactionRequest(
    @SerializedName("notification_id") val notification_id: Int,
    @SerializedName("reaction_id") val reaction_id: Int
)