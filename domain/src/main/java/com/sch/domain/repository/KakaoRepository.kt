package com.sch.domain.repository

import com.sch.domain.NetworkResult
import com.sch.domain.model.ResultSearchKeyword
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named


interface KakaoRepository {
    suspend fun getResultKeyword(keyword : String, page : Int): Response<ResultSearchKeyword>
}