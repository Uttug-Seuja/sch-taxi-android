package com.sch.data.model.remote.request

import com.google.gson.annotations.SerializedName


data class PatchUserProfileRequest(
    @SerializedName("profilePath") val profilePath: String
)