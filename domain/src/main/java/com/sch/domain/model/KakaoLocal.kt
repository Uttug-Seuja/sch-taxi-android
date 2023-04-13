package com.sch.domain.model

// 리사이클러 뷰 아이템 클래스
data class KakaoLocal(
    val name: String,      // 장소명
    val road: String,      // 도로명 주소
    val address: String,   // 지번 주소
    val x: Double,         // 경도(Longitude)
    val y: Double          // 위도(Latitude)
    )
