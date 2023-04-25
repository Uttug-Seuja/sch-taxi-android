package com.sch.data.api

import com.sch.data.model.remote.request.*
import com.sch.data.model.remote.response.*
import okhttp3.MultipartBody
import retrofit2.http.*

interface MainAPIService {

    // 등록 요청 <- 1번 false일 경우
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

    // 회원 탈퇴
    @DELETE("/api/v1/credentials/me")
    suspend fun deleteUser(@Query("oauthAccessToken") oauthAccessToken: String): Unit

    // 유저 프로필
    @GET("/api/v1/users/profile")
    suspend fun getUserProfile(): BaseResponse<UserProfileResponse>

    /***
    PUT : 자원의 전체 교체, 자원교체 시 모든 필드 필요
    (만약 전체가 아닌 일부만 전달할 경우,
    전달한 필드외 모두 null or 초기값 처리되니 주의!!)
    PATCH : 자원의 부분 교체, 자원교체시 일부 필드 필요
     * */

    // 유저 프로필 변경
    @PUT("/api/v1/users/profile")
    suspend fun patchUserProfile(@Body body: PatchUserProfileRequest): BaseResponse<UserProfileResponse>

    // 예약 만들기
    @POST("/api/v1/groups/open")
    suspend fun postReservation(@Body body: PostReservationRequest): BaseResponse<ReservationResponse>

    // 예약 삭제
    @DELETE("/api/v1/groups/{id}")
    suspend fun deleteReservation(@Path("reservationId") reservationId: Int): Unit

    // 예약 수정
    @PUT("/api/v1/groups/{id}")
    suspend fun patchReservation(
        @Path("reservationId") reservationId: Int,
        @Body body: PatchReservationRequest
    ): BaseResponse<ReservationResponse>

    // 예약 상세정보
    @GET("/api/v1/storages")
    suspend fun getReservationDetail(@Path("reservationId") reservationId: Int): BaseResponse<ReservationResponse>

    // 예약 전체 조회
    @GET("/api/v1/storages")
    suspend fun getReservation(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): BaseResponse<PagingReservationResponse>

    // 내 예약 글
    @GET("/api/v1/storages")
    suspend fun getUserReservation(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): BaseResponse<PagingReservationResponse>

    // 예약 키워드 검색하기
    @GET("/api/v1/storages")
    suspend fun getReservationKeyword(
        @Body body: GetReservationKeywordRequest,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): BaseResponse<ReservationKeywordResponse> // 나중에 해야흠

    // 예약 검색하기
    @GET("/api/v1/storages")
    suspend fun getReservationSearch(
        @Body body: GetReservationKeywordRequest,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): BaseResponse<PagingReservationResponse>

    // 추천 키워드 조회
    @GET("/api/v1/recommendmessage")
    suspend fun getRecommendKeyword(): BaseResponse<RecommendKeywordListResponse>

    // 참여하기
    @POST("/api/v1/groups/open")
    suspend fun postParticipation(
        @Path("reservationId") id: Int,
        @Body seatPosition: String
    ): BaseResponse<ParticipationResponse>

    // 참여 취소하기
    @DELETE("/api/v1/groups/open")
    suspend fun deleteParticipation(@Path("participationId") id: Int): Unit

    // 참여 수정하기
    @PATCH("/api/v1/groups/open")
    suspend fun patchParticipation(
        @Path("participationId") id: Int,
        @Body seatPosition: String
    ): BaseResponse<ParticipationResponse>

    // 내가 예약 했는지 확인
    @GET("/api/v1/participation")
    suspend fun getParticipation(@Path("id") id: Int): BaseResponse<ParticipationResponse>

    // 내가 참여한 예약글
    @GET("/api/v1/storages")
    suspend fun getUserParticipation(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): BaseResponse<PagingReservationResponse>

    // 야간 푸시알림 설정 <- 마이페이지
    @POST("/api/v1/options/night")
    suspend fun postOptionNight(): Unit

    // 야간 푸시알림 설정하제 <- 마이페이지
    @DELETE("/api/v1/options/night")
    suspend fun deleteOptionNight(): Unit

    // 새로운 푸시알림 설정 <- 마이페이지
    @POST("/api/v1/options/new")
    suspend fun postOptionNew(): Unit

    // 새로운 푸시알림 설정 해제 <- 마이페이지
    @DELETE("/api/v1/options/new")
    suspend fun deleteOptionNew(): Unit

    // 유저의 알림 세팅
    @GET("/api/v1/options")
    suspend fun getOptions(): BaseResponse<OptionsResponse>

    // 최신 푸쉬 알림 리스트
    @GET("/api/v1/notifications")
    suspend fun getNotifications(): BaseResponse<NotificationListResponse>

    // 푸쉬 알림 보내기
    @POST("/api/v1/notifications")
    suspend fun postNotifications(@Body body: PostNotificationRequest): Unit

    // FCM 토큰 등록
    @POST("/api/v1/notifications/token")
    suspend fun postNotificationToken(@Body body: PostNotificationTokenRequest): Unit

    // 파일 URL로 바꾸기
    @Multipart
    @POST("/api/v1/images")
    suspend fun postFileToUrl(@Part file: MultipartBody.Part): BaseResponse<ImageUrlResponse>

    // 그룹에서 나가기
    @DELETE("/api/v1/groups/{id}/members/leave")
    suspend fun deleteLeaveGroup(@Path("id") id: Int): BaseResponse<GroupResponse>

    // 프로필 이미지
    @GET("/api/v1/asset/profiles")
    suspend fun getProfiles(): BaseResponse<ProfileListResponse>

    // 알림 가져오기
    @GET("/api/v1/alarms")
    suspend fun getAlarms(): BaseResponse<AlarmListResponse>

    // 알림 있는지 확인
    @GET("/api/v1/alarms/count")
    suspend fun getAlarmsCount(): BaseResponse<AlarmCountResponse>

    // 유저 신고하기
    @POST("/api/v1/reports/notifications/{notification_id}")
    suspend fun postReportsNotifications(
        @Path("notification_id") notification_id: Int,
        @Body body: PostReportsNotificationRequest
    ): BaseResponse<ReportNotificationResponse>
//    ): BaseResponse<Unit>


}