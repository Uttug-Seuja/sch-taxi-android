package com.sch.domain.repository

import com.sch.domain.NetworkResult
import com.sch.domain.model.*
import okhttp3.MultipartBody

interface MainRepository {
    // 등록 요청
    suspend fun postRegister(
        idToken: String,
        provider: String,
        name: String,
        gender: String,
        profilePath: String,
        schEmail: String,
    ): NetworkResult<Token>

    // 토큰 리프래쉬
    suspend fun postRefreshToken(refreshToken: String): NetworkResult<Token>

    // 로그인 요청 <- 가입한 유저
    suspend fun postLogin(idToken: String, provider: String): NetworkResult<Token>

    // 로그아웃
    suspend fun postLogout(): NetworkResult<Unit>

    // 토큰 검증
    suspend fun getTokenValidation(idToken: String, provider: String): NetworkResult<IsRegistered>

    // 회원 탈퇴
//    suspend fun deleteUser(oauthAccessToken: String): NetworkResult<Unit>

    // 유저 프로필
    suspend fun getUserProfile(): NetworkResult<UserInfo>

    suspend fun getOtherProfile(userId: Int): NetworkResult<UserInfo>

    suspend fun getAssetRandom(): NetworkResult<AssetRandom>


    // 유저 프로필 변경
    suspend fun patchUserProfile(profilePath: String): NetworkResult<UserInfo>

    // 예약 만들기
    suspend fun postReservation(
        title: String,
        startPoint: String,
        destination: String,
        departureDate: String,
        gender: String,
        startLatitude: Double,
        startLongitude: Double,
        destinationLatitude: Double,
        destinationLongitude: Double,
    ): NetworkResult<Reservation>

    // 예약 수정
    suspend fun patchReservation(
        reservationId: Int,
        title: String,
        startPoint: String,
        destination: String,
        departureDate: String,
        startLatitude: Double,
        startLongitude: Double,
        destinationLatitude: Double,
        destinationLongitude: Double,
    ): NetworkResult<Reservation>

    // 예약 삭제
    suspend fun deleteReservation(reservationId: Int): NetworkResult<Unit>

    // 예약 상세정보
    suspend fun getReservationDetail(reservationId: Int): NetworkResult<ReservationDetail>

    // 예약 전체 조회
    suspend fun getReservation(page: Int, size: Int): NetworkResult<PagingReservation>

    // 예약 키워드 검색하기
    suspend fun getReservationKeyword(
        word: String,
        page: Int,
        size: Int,
    ): NetworkResult<PagingReservationKeyword>

    // 예약 검색하기
    suspend fun getReservationSearch(
        keyword: String, page: Int, size: Int,
    ): NetworkResult<PagingReservation>

    // 참여하기
    suspend fun postParticipation(reservationId: Int, seatPosition: String): NetworkResult<Unit>

    // 참여 수정하기
    suspend fun patchParticipation(
        participationId: Int,
        seatPosition: String,
    ): NetworkResult<Unit>

    // 참여 취소하기
    suspend fun deleteParticipation(id: Int): NetworkResult<Unit>

    // 내가 예약 했는지 확인
    suspend fun getParticipation(id: Int): NetworkResult<Participation>

    // 내 예약 글
    suspend fun getUserReservation(): NetworkResult<List<Reservation>>

    // 내가 참여한 예약
    suspend fun getUserParticipation(): NetworkResult<List<Reservation>>

    //    // 야간 푸시알림 설정 <- 마이페이지
//    suspend fun postOptionNight(): NetworkResult<Unit>
//
//    // 야간 푸시알림 설정하제 <- 마이페이지
//    suspend fun deleteOptionNight(): NetworkResult<Unit>
//
//    // 새로운 푸시알림 설정 <- 마이페이지
//    suspend fun postOptionNew(): NetworkResult<Unit>
//
//    // 새로운 푸시알림 설정 해제 <- 마이페이지
//    suspend fun deleteOptionNew(): NetworkResult<Unit>
//
//    // 유저의 알림 세팅
//    suspend fun getOptions(): NetworkResult<Options>
//
    // 최신 푸쉬 알림 리스트
    suspend fun getNotification(page: Int, size: Int): NetworkResult<NotificationList>

    //
//    // 푸쉬 알림 보내기
//    suspend fun postNotifications(
//        group_id: Int, title: String, content: String, image_url: String
//    ): NetworkResult<Unit>
//
//    // FCM 토큰 등록
    suspend fun postNotificationToken(deviceId: String, token: String): NetworkResult<Unit>

    // 파일 URL로 바꾸기
    suspend fun postFileToUrl(
        file: MultipartBody.Part,
    ): NetworkResult<ImageUrl?>

    // 그룹에서 나가기
//    suspend fun deleteLeaveGroup(id: Int): NetworkResult<Group>

    // 추천 키워드 조회
    suspend fun getRecommendKeyword(): NetworkResult<List<Keyword>>

    // 앱버젼 체크
//    suspend fun getAppVersion(): NetworkResult<AppVersion>

    // 프로필 이미지
//    suspend fun getProfiles(): NetworkResult<ProfileList>

    // 알림 가져오기
//    suspend fun getAlarms(): NetworkResult<AlarmList>
//
//    // 알림 있는지 확인
//    suspend fun getAlarmsCount(): NetworkResult<AlarmCount>

    // 알림 신고하기
    suspend fun postReportsParticipation(
        participationId: Int, reportReason: String, reportType: String,
    ): NetworkResult<ReportNotification>

    suspend fun postEmail(
        email: String,
        oauthProvider: String,
    ): NetworkResult<Unit>

    suspend fun postEmailCode(
        email: String,
        code: String,
        oauthProvider: String,
    ): NetworkResult<Unit>

    suspend fun postChat(
        reservationId: Int,
        message: String,
        writer: String,
        cursor: String,
        userId: Int,
        participationId: Int,
    ): NetworkResult<PagingChat>

    suspend fun getChatRoom(): NetworkResult<List<ChatRoom>>

}