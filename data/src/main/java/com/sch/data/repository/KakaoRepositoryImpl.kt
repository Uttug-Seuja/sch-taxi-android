package com.sch.data.repository



import com.sch.data.BuildConfig
import com.sch.data.api.KakaoAPIService
import com.sch.data.api.handleApi
import com.sch.domain.NetworkResult
import com.sch.domain.model.ResultSearchKeyword
import com.sch.domain.repository.KakaoRepository
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named


class KakaoRepositoryImpl @Inject constructor(
    @Named("Kakao") private val kakaoAPIService: KakaoAPIService
) : KakaoRepository {

    override suspend fun getResultKeyword(
        keyword: String,
        page: Int
    ): Response<ResultSearchKeyword> {
        return  kakaoAPIService.getSearchKeyword(key= BuildConfig.KAKAO_LOCAL_REST_APP_KEY, query = keyword, page = page)
    }
}