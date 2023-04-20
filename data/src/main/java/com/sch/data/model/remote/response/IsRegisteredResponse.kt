package com.sch.data.model.remote.response

import com.google.gson.annotations.SerializedName

data class IsRegisteredResponse(
    @SerializedName("isRegistered") val isRegistered: Boolean
)