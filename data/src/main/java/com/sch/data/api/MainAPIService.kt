package com.sch.data.api

import com.sch.data.model.remote.request.PostRefreshTokenRequest
import com.sch.data.model.remote.request.PostSignUpRequest
import com.sch.data.model.remote.request.PostUserProfileRequest
import com.sch.data.model.remote.response.BaseResponse
import com.sch.domain.model.ImageUrl
import com.sch.domain.model.LogInStatus
import com.sch.domain.model.LoginResponse
import com.sch.domain.model.UserProfile
import okhttp3.MultipartBody
import retrofit2.http.*

interface MainAPIService {
    // 토큰 리프래쉬
    @POST("/api/v1/credentials/refresh")
    suspend fun postRefreshToken(@Body body: PostRefreshTokenRequest): BaseResponse<LoginResponse>

    // 로그인(회원인지 판단)
    @POST("api/v1/user/signIn")
    suspend fun postLogin(
        @Query("idTokenString") idTokenString: String
    ): BaseResponse<LogInStatus>

    // 회원가입
    @POST("/api/v1/user/signUp")
    suspend fun postSignUp(
        @Body body: PostSignUpRequest
    ): Unit

    // 로그아웃
    @POST("/api/v1/user/logOut")
    suspend fun postLogOut(): Unit

    // 유저 프로필
    @GET("/api/v1/user/profile")
    suspend fun getUserProfile(): BaseResponse<UserProfile>

    // 유저 프로필 변경
    @POST("/api/v1/user/change")
    suspend fun postUserProfile(
        @Body body: PostUserProfileRequest
    ): Unit

    // 파일 URL로 바꾸기
    @Multipart
    @POST("/api/v1/images/upload")
    suspend fun postFileToUrl(@Part file: MultipartBody.Part): BaseResponse<ImageUrl>

    // 랜덤 기본 프로필 이미지 가져오기
    @GET("/api/v1/asset/profile/random")
    suspend fun getRandomProfileImage(): BaseResponse<ImageUrl>

}