package com.sch.domain.model

data class Chat (
    val reservationId : Int,
    val userId : Int,
    val writer : String,
    val message : String,
    val createdAt : String,
    val profilePath : String,
    val isend : Boolean


)

//"reservationId": 1,
//"userId": 1,
//"writer": "이훈일",
//"message": "ㅇㅋ",
//"createdAt": "2023/08/19 14:05:20.718",
//"profilePath": "https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7C098f3311-eae2-4f51-8ae7-90b4fa0887fc.jpeg"