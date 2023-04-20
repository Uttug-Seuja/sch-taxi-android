package com.sch.data.model.remote.request

import com.google.gson.annotations.SerializedName


data class PutUserProfileRequest(
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profilePath") val profilePath: String
)

//{
//    "nickname": "string",
//    "profile_path": "string"
//}