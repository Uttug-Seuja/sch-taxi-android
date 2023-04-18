package com.sch.data.model.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("success") val success: Boolean,
    @SerializedName("status") val status: Int,
    @SerializedName("data") val data: T,
    @SerializedName("time_stamp") val time_stamp: String
)