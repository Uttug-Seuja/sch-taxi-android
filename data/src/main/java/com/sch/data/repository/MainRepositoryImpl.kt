package com.sch.data.repository


import com.sch.data.api.MainAPIService
import com.sch.data.api.handleApi
import com.sch.data.model.remote.request.*
import com.sch.data.model.remote.response.KeywordResponse
import com.sch.data.model.toDomain
import com.sch.domain.NetworkResult
import com.sch.domain.model.*
import com.sch.domain.repository.MainRepository
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Named

class MainRepositoryImpl @Inject constructor(
    @Named("Main") private val mainAPIService: MainAPIService
) : MainRepository {

    override suspend fun postRegister(
        idToken: String,
        provider: String,
        name: String,
        gender: String,
        profilePath: String
    ): NetworkResult<Token> {
        val body = PostRegisterRequest(
            name = name,
            gender = gender,
            profilePath = profilePath
        )
        return handleApi {
            mainAPIService.postRegister(
                idToken = idToken,
                provider = provider,
                body = body
            ).data.toDomain()
        }
    }

    override suspend fun postRefreshToken(refreshToken: String): NetworkResult<Token> {
        val body = PostRefreshTokenRequest(refreshToken = refreshToken)
        return handleApi { mainAPIService.postRefreshToken(body = body).data.toDomain() }
    }

    override suspend fun postLogin(idToken: String, provider: String): NetworkResult<Token> {
        return handleApi {
            mainAPIService.postLogin(
                idToken = idToken,
                provider = provider
            ).data.toDomain()
        }
    }

    override suspend fun postLogout(): NetworkResult<Unit> {
        return handleApi { mainAPIService.postLogout() }
    }

    override suspend fun getTokenValidation(
        idToken: String,
        provider: String
    ): NetworkResult<IsRegistered> {
        return handleApi {
            mainAPIService.getTokenValidation(
                idToken = idToken,
                provider = provider
            ).data.toDomain()
        }
    }

//    override suspend fun deleteUser(oauthAccessToken: String): NetworkResult<Unit> {
//        return handleApi { mainAPIService.deleteUser(oauthAccessToken = oauthAccessToken) }
//    }

    override suspend fun getUserProfile(): NetworkResult<UserInfo> {
        return handleApi { mainAPIService.getUserProfile().data.toDomain() }
    }

    override suspend fun getOtherProfile(userId: Int): NetworkResult<UserInfo> {
        return handleApi { mainAPIService.getOtherProfile(userId = userId).data.toDomain() }
    }

    override suspend fun getAssetRandom(): NetworkResult<AssetRandom> {
        return handleApi { mainAPIService.getAssetRandom().data.toDomain() }
    }

    override suspend fun patchUserProfile(
        profilePath: String
    ): NetworkResult<UserInfo> {
        val body = PatchUserProfileRequest(profilePath = profilePath)
        return handleApi { mainAPIService.patchUserProfile(body = body).data.toDomain() }
    }

    override suspend fun postReservation(
        title: String,
        startPoint: String,
        destination: String,
        departureDate: String,
        gender: String,
        startLatitude: Double,
        startLongitude: Double,
        destinationLatitude: Double,
        destinationLongitude: Double
    ): NetworkResult<Reservation> {
        val body = PostReservationRequest(
            title = title,
            departureDate = departureDate,
            gender = gender,
            startPoint = startPoint,
            destination = destination,
            startLatitude = startLatitude,
            startLongitude = startLongitude,
            destinationLatitude = destinationLatitude,
            destinationLongitude = destinationLongitude
        )
        return handleApi { mainAPIService.postReservation(body = body).data.toDomain() }
    }

    override suspend fun patchReservation(
        reservationId: Int,
        title: String,
        startPoint: String,
        destination: String,
        departureDate: String,
        startLatitude: Double,
        startLongitude: Double,
        destinationLatitude: Double,
        destinationLongitude: Double
    ): NetworkResult<Reservation> {
        val body = PatchReservationRequest(
            title = title,
            departureDate = departureDate,
            startPoint = startPoint,
            destination = destination,
            startLatitude = startLatitude,
            startLongitude = startLongitude,
            destinationLatitude = destinationLatitude,
            destinationLongitude = destinationLongitude
        )
        return handleApi {
            mainAPIService.patchReservation(
                reservationId = reservationId,
                body = body
            ).data.toDomain()
        }
    }

    override suspend fun deleteReservation(reservationId: Int): NetworkResult<Unit> {
        return handleApi { mainAPIService.deleteReservation(reservationId = reservationId) }
    }

    override suspend fun getReservationDetail(reservationId: Int): NetworkResult<ReservationDetail> {
        return handleApi { mainAPIService.getReservationDetail(reservationId = reservationId).data.toDomain() }
    }

    override suspend fun getReservation(
        page: Int,
        size: Int,
    ): NetworkResult<PagingReservation> {
        return handleApi {
            mainAPIService.getReservation(
                page = page,
                size = size,
            ).data.toDomain()
        }
    }

    override suspend fun getReservationKeyword(
        word: String,
        page: Int,
        size: Int
    ): NetworkResult<PagingReservationKeyword> {
        return handleApi {
            mainAPIService.getReservationKeyword(
                word = word,
                page = page,
                size = size
            ).data.toDomain()
        }

    }

    override suspend fun getReservationSearch(
        word: String,
        page: Int,
        size: Int,
    ): NetworkResult<PagingReservation> {
        return handleApi {
            mainAPIService.getReservationSearch(
                word = word,
                page = page,
                size = size,
            ).data.toDomain()
        }
    }

    override suspend fun postParticipation(
        reservationId: Int,
        seatPosition: String
    ): NetworkResult<Unit> {
        val body = SeatPositionRequest(seatPosition)
        return handleApi {
            mainAPIService.postParticipation(
                reservationId = reservationId,
                body = body
            )
        }
    }

    override suspend fun patchParticipation(
        participationId: Int,
        seatPosition: String
    ): NetworkResult<Unit> {
        val body = SeatPositionRequest(seatPosition= seatPosition)
        return handleApi {
            mainAPIService.patchParticipation(
                id = participationId,
                body = body
            )
        }
    }

    override suspend fun deleteParticipation(id: Int): NetworkResult<Unit> {
        return handleApi { mainAPIService.deleteParticipation(id = id) }
    }

    override suspend fun getParticipation(id: Int): NetworkResult<Participation> {
        return handleApi { mainAPIService.getParticipation(id = id).data.toDomain() }
    }

    override suspend fun getUserReservation(): NetworkResult<List<Reservation>> {
        return handleApi {
            mainAPIService.getUserReservation().data.toDomain()
        }
    }

    override suspend fun getUserParticipation(): NetworkResult<List<Reservation>> {
        return handleApi {
            mainAPIService.getUserParticipation().data.toDomain()
        }
    }

//    override suspend fun postOptionNight(): NetworkResult<Unit> {
//        return handleApi { mainAPIService.postOptionNight() }
//    }
//
//    override suspend fun deleteOptionNight(): NetworkResult<Unit> {
//        return handleApi { mainAPIService.deleteOptionNight() }
//    }
//
//    override suspend fun postOptionNew(): NetworkResult<Unit> {
//        return handleApi { mainAPIService.postOptionNew() }
//    }
//
//    override suspend fun deleteOptionNew(): NetworkResult<Unit> {
//        return handleApi { mainAPIService.deleteOptionNew() }
//    }
//
//    override suspend fun getOptions(): NetworkResult<Options> {
//        return handleApi { mainAPIService.getOptions().data.toDomain() }
//    }
//
//    override suspend fun getNotifications(): NetworkResult<NotificationList> {
//        return handleApi { mainAPIService.getNotifications().data.notifications.toDomain() }
//    }

//    override suspend fun postNotifications(
//        group_id: Int,
//        title: String,
//        content: String,
//        image_url: String
//    ): NetworkResult<Unit> {
//        val body = PostNotificationRequest(
//            group_id = group_id,
//            title = title,
//            content = content,
//            image_url = image_url
//        )
//        return handleApi { mainAPIService.postNotifications(body = body) }
//
//    }
//
//    override suspend fun postNotificationToken(
//        deviceId: String,
//        token: String
//    ): NetworkResult<Unit> {
//        val body = PostNotificationTokenRequest(deviceId = deviceId, token = token)
//        return handleApi {
//            mainAPIService.postNotificationToken(body = body)
//        }
//    }

    override suspend fun postFileToUrl(file: MultipartBody.Part): NetworkResult<ImageUrl> {
        return handleApi { mainAPIService.postFileToUrl(file = file).data.toDomain() }
    }

//    override suspend fun deleteLeaveGroup(id: Int): NetworkResult<Group> {
//        TODO("Not yet implemented")
//    }

    override suspend fun getRecommendKeyword(): NetworkResult<List<Keyword>> {

        return handleApi {
            mainAPIService.getRecommendKeyword().data.toDomain()
        }
    }

//    override suspend fun getAppVersion(): NetworkResult<AppVersion> {
//        TODO("Not yet implemented")
//    }

//    override suspend fun getProfiles(): NetworkResult<ProfileList> {
//        return handleApi { mainAPIService.getProfiles().data.profiles.toDomain() }
//    }

    override suspend fun postReportsParticipation(
        participationId: Int,
        reportReason: String,
        reportType: String
    ): NetworkResult<ReportNotification> {
        val body =
            PostReportsNotificationRequest(reportReason = reportReason, reportType = reportType)
        return handleApi {
            mainAPIService.postReportsParticipation(
                participationId = participationId,
                body = body
            ).data.toDomain()
        }
    }

    override suspend fun postEmail(email: String): NetworkResult<Unit> {
        val body = EmailRequest(email = email)
        return handleApi { mainAPIService.postEmail(body) }
    }

    override suspend fun postEmailCode(email: String, code: String): NetworkResult<Unit> {
        val body = EmailCodeRequest(email = email, code = code)
        return handleApi { mainAPIService.postEmailCode(body) }
    }

//    override suspend fun getAlarms(): NetworkResult<AlarmList> {
//        return handleApi { mainAPIService.getAlarms().data.list.toDomain() }
//    }

//    override suspend fun getAlarmsCount(): NetworkResult<AlarmCount> {
//        TODO("Not yet implemented")
//    }


}