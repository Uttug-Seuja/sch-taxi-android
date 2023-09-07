package com.sch.data.model.remote.response

import com.google.gson.annotations.SerializedName

data class ChatResponse(
    @SerializedName("reservationId") val reservationId: Int,
    @SerializedName("participationId") val participationId: Int,
    @SerializedName("userId") val userId: Int,
    @SerializedName("writer") val writer: String,
    @SerializedName("message") val message: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("profilePath") val profilePath: String,
    @SerializedName("isend") val isend: Boolean

)


//"reservationId": 1,
//"userId": 1,
//"writer": "이훈일",
//"message": "ㅇㅋ",
//"createdAt": "2023/08/19 14:05:20.718",
//"profilePath": "https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7C098f3311-eae2-4f51-8ae7-90b4fa0887fc.jpeg"