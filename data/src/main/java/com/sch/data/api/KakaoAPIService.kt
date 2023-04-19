package com.sch.data.api

import com.sch.domain.model.*
import retrofit2.Response
import retrofit2.http.*

interface KakaoAPIService {

    @GET("v2/local/search/keyword.json")    // Keyword.json의 정보를 받아옴
    suspend fun getSearchKeyword(
        @Header("Authorization") key: String,     // 카카오 API 인증키 [필수]
        @Query("query") query: String,            // 검색을 원하는 질의어 [필수]
        @Query("page") page: Int                 // 결과 페이지 번호
    ): Response<ResultSearchKeyword>    // 받아온 정보가 ResultSearchKeyword 클래스의 구조로 담김
}