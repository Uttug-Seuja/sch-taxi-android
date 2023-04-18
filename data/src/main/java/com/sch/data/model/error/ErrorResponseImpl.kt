package com.sch.data.model.error

import com.google.gson.annotations.SerializedName
import com.sch.data.model.error.ErrorResponse

data class ErrorResponseImpl(
    @SerializedName("success") override val success: Boolean,
    @SerializedName("status") override val status: Int,
    @SerializedName("code") override val code: String,
    @SerializedName("reason") override val reason: String,
    @SerializedName("time_stamp") override val time_stamp: String,
    @SerializedName("path") override val path: String
) : ErrorResponse


