package com.sch.data.model.remote.request

import com.google.gson.annotations.SerializedName

data class PostBookmarkRequest(
    @SerializedName("bookMarkName") val bookMarkName: String,
    @SerializedName("moodImageUrl") val moodImageUrl: String,
    @SerializedName("summary") val summary: String,
    @SerializedName("checkPageNum") val checkPageNum: Int,
    @SerializedName("color") val color: String
)