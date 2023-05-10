package com.sch.data.api

import com.sch.data.model.remote.request.*
import com.sch.data.model.remote.response.*
import okhttp3.MultipartBody
import retrofit2.http.*

interface MainAPIService {

    // 등록 요청 <- 1번 false일 경우 // 회원가입
    @POST("/api/v1/credentials/register")
    suspend fun postRegister(
        @Query("idToken") idToken: String,
        @Query("provider") provider: String,
        @Body body: PostRegisterRequest
    ): BaseResponse<LoginResponse>

    // 토큰 리프래쉬
    @POST("/api/v1/credentials/refresh")
    suspend fun postRefreshToken(@Body body: PostRefreshTokenRequest): BaseResponse<LoginResponse>

    // 로그인 요청 <- 가입한 유저 <- 1번 true일 경우
    @POST("/api/v1/credentials/login")
    suspend fun postLogin(
        @Query("idToken") idToken: String, @Query("provider") provider: String
    ): BaseResponse<LoginResponse>

    // 로그아웃
    @POST("/api/v1/credentials/logout")
    suspend fun postLogout(): Unit

    // 토큰 검증 <- 로그인시 제일 처음 1번
    @GET("/api/v1/credentials/oauth/valid/register")
    suspend fun getTokenValidation(
        @Query("idToken") idToken: String, @Query("provider") provider: String
    ): BaseResponse<IsRegisteredResponse>

//    // 회원 탈퇴
//    @DELETE("/api/v1/credentials/me")
//    suspend fun deleteUser(@Query("oauthAccessToken") oauthAccessToken: String): Unit

    // 내 프로필 가져오기
    @GET("/api/v1/user/profile")
    suspend fun getUserProfile(): BaseResponse<UserInfoResponse>

    // 다른 사람 프로필 가져오기
    @GET("/api/v1/user/other-profile")
    suspend fun getOtherProfile(
        @Path("userId") userId: Int
    ): BaseResponse<UserInfoResponse>

    // 유저 프로필 사진 변경하기
    @PUT("/api/v1/user/profile")
    suspend fun patchUserProfile(@Body body: PatchUserProfileRequest): BaseResponse<UserInfoResponse>

    // 예약 글 생성
    @POST("/api/v1/reservation/create")
    suspend fun postReservation(@Body body: PostReservationRequest): BaseResponse<ReservationResponse>

    // 예약 삭제
    @DELETE("/api/v1/reservation/{reservationId}")
    suspend fun deleteReservation(@Path("reservationId") reservationId: Int): Unit

    // 예약 수정
    @PUT("/api/v1/reservation/{reservationId}")
    suspend fun patchReservation(
        @Path("reservationId") reservationId: Int,
        @Body body: PatchReservationRequest
    ): BaseResponse<ReservationResponse>

    // 예약 상세정보
    @GET("/api/v1/reservation")
    suspend fun getReservationDetail(@Path("reservationId") reservationId: Int): BaseResponse<ReservationResponse>

    // 예약 전체 조회
    @GET("/api/v1/reservation/list")
    suspend fun getReservation(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): BaseResponse<PagingReservationResponse>

    // 내 예약 글
    @GET("/api/v1/reservation/my")
    suspend fun getUserReservation(): BaseResponse<MyReservationResponse>

    // 예약 키워드 검색하기
    @GET("/api/v1/reservation/search/keyword")
    suspend fun getReservationKeyword(
        @Body body: GetReservationKeywordRequest,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): BaseResponse<PagingReservationKeywordResponse> // 나중에 해야흠

    // 예약 검색하기
    @GET("/api/v1/search")
    suspend fun getReservationSearch(
        @Body body: GetReservationWordRequest,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): BaseResponse<PagingReservationResponse>

    // 추천 검색어
    @GET("/api/v1/reservation/search/recommend")
    suspend fun getRecommendKeyword(): BaseResponse<RecommendKeywordListResponse>

    // 참여하기
    @POST("/api/v1/participation/create/{reservationId}")
    suspend fun postParticipation(
        @Path("reservationId") id: Int,
        @Body seatPosition: String
    ): Unit

    // 참여 취소하기
    @DELETE("api/v1/participation/delete/{reservationId}")
    suspend fun deleteParticipation(@Path("participationId") id: Int): Unit

    // 내가 참여한 게시글의 좌석 변경
    @PATCH("/api/v1/participation/update/{reservationId}")
    suspend fun patchParticipation(
        @Path("participationId") id: Int,
        @Body seatPosition: String
    ): Unit

    // 해당 게시글의 참여자 리스트 조회 및 내가 참여했는지 확인
    @GET("/api/v1/participation/getParticipationList")
    suspend fun getParticipation(@Path("id") id: Int): BaseResponse<ParticipationResponse>

    // 내가 참여한 예약글
    @GET("/api/v1/reservation/my/participation")
    suspend fun getUserParticipation(): BaseResponse<MyReservationResponse>

//    // 야간 푸시알림 설정 <- 마이페이지
//    @POST("/api/v1/options/night")
//    suspend fun postOptionNight(): Unit
//
//    // 야간 푸시알림 설정하제 <- 마이페이지
//    @DELETE("/api/v1/options/night")
//    suspend fun deleteOptionNight(): Unit
//
//    // 새로운 푸시알림 설정 <- 마이페이지
//    @POST("/api/v1/options/new")
//    suspend fun postOptionNew(): Unit
//
//    // 새로운 푸시알림 설정 해제 <- 마이페이지
//    @DELETE("/api/v1/options/new")
//    suspend fun deleteOptionNew(): Unit
//
//    // 유저의 알림 세팅
//    @GET("/api/v1/options")
//    suspend fun getOptions(): BaseResponse<OptionsResponse>
//
//    // 최신 푸쉬 알림 리스트
//    @GET("/api/v1/notifications")
//    suspend fun getNotifications(): BaseResponse<NotificationListResponse>
//
//    // 푸쉬 알림 보내기
//    @POST("/api/v1/notifications")
//    suspend fun postNotifications(@Body body: PostNotificationRequest): Unit
//
//    // FCM 토큰 등록
//    @POST("/api/v1/notifications/token")
//    suspend fun postNotificationToken(@Body body: PostNotificationTokenRequest): Unit

    // 파일 URL로 바꾸기
    @Multipart
    @POST("/api/v1/images/upload")
    suspend fun postFileToUrl(@Part file: MultipartBody.Part): BaseResponse<ImageUrlResponse>

//    // 그룹에서 나가기
//    @DELETE("/api/v1/groups/{id}/members/leave")
//    suspend fun deleteLeaveGroup(@Path("id") id: Int): BaseResponse<GroupResponse>

//    // 프로필 이미지
//    @GET("/api/v1/asset/profiles")
//    suspend fun getProfiles(): BaseResponse<ProfileListResponse>

//    // 알림 가져오기
//    @GET("/api/v1/alarms")
//    suspend fun getAlarms(): BaseResponse<AlarmListResponse>
//
//    // 알림 있는지 확인
//    @GET("/api/v1/alarms/count")
//    suspend fun getAlarmsCount(): BaseResponse<AlarmCountResponse>

    // 유저 신고하기
    @POST("/api/v1/report/create/{participationId}")
    suspend fun postReportsParticipation(
        @Path("participationId") participationId: Int,
        @Body body: PostReportsNotificationRequest
    ): BaseResponse<ReportNotificationResponse>
//    ): BaseResponse<Unit>

    @POST("/api/v1/email")
    suspend fun postEmail(
        @Body body: EmailRequest
    ): BaseResponse<Unit>

    @POST("/api/v1/email/code")
    suspend fun postEmailCode(
        @Body body: EmailCodeRequest
    ): BaseResponse<Unit>



}