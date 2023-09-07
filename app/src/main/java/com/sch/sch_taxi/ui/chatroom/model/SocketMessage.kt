package com.sch.sch_taxi.ui.chatroom.model

data class SocketMessage(
    val userId: Int,
    val participationId: Int,
    val type: String,
    val roomId: String,
    val writer: String,
    val profilePath: String,
    val message: String,
    val createdAt: String,
    val userList: List<String>?
)

//{"userId":11,"type":"TALK","roomId":"2","writer":"조준장","profilePath":"https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7C737421e7-e595-4349-9ecb-d2363e821463.jpeg"
// ,"message":"병신드라 뭐하냐","createdAt":"2023/08/28 00:40:52.409","userList":null}??
