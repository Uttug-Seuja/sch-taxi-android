package com.sch.data.repository


import com.sch.data.api.MainAPIService
import com.sch.data.api.handleApi
import com.sch.data.model.remote.request.*
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
        nickname: String,
        profilePath: String
    ): NetworkResult<Token> {
        val body = PostRegisterRequest(nickname = nickname, profilePath = profilePath)
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

    override suspend fun deleteUser(oauthAccessToken: String): NetworkResult<Unit> {
        return handleApi { mainAPIService.deleteUser(oauthAccessToken = oauthAccessToken) }
    }

    override suspend fun getUserProfile(): NetworkResult<UserProfile> {
        return handleApi { mainAPIService.getUserProfile().data.toDomain() }
    }

    override suspend fun putUserProfile(
        nickname: String,
        profilePath: String
    ): NetworkResult<UserProfile> {
        val body = PutUserProfileRequest(nickname = nickname, profilePath = profilePath)
        return handleApi { mainAPIService.putUserProfile(body = body).data.toDomain() }
    }

    override suspend fun postReservation(
        title: String,
        reserveDate: String,
        sex: String,
        startingPlace: String,
        destination: String,
        challengeWord: String,
        countersignWord: String,
        startLatitude: Double,
        startLongitude: Double,
        finishLatitude: Double,
        finishLongitude: Double
    ): NetworkResult<Reservation> {
        val body = PostReservationRequest(
            title = title,
            reserveDate = reserveDate,
            sex = sex,
            startingPlace = startingPlace,
            destination = destination,
            challengeWord = challengeWord,
            countersignWord = countersignWord,
            startLatitude = startLatitude,
            startLongitude = startLongitude,
            finishLatitude = finishLatitude,
            finishLongitude = finishLongitude
        )
        return handleApi { mainAPIService.postReservation(body = body).data.toDomain() }
    }

    override suspend fun putReservation(id: Int, title: String): NetworkResult<Reservation> {
        val body = PutReservationRequest(title = title)
        return handleApi { mainAPIService.putReservation(id = id, body = body).data.toDomain() }
    }

    override suspend fun deleteReservation(id: Int): NetworkResult<Unit> {
        return handleApi { mainAPIService.deleteReservation(id = id) }
    }

    override suspend fun getReservationDetail(id: Int): NetworkResult<ReservationDetail> {
        return handleApi { mainAPIService.getReservationDetail(id = id).data.toDomain() }
    }

    override suspend fun getReservation(
        page: Int,
        size: Int,
        sort: String?
    ): NetworkResult<PagingReservations> {
        return handleApi {
            mainAPIService.getReservation(
                page = page,
                size = size,
                sort = sort
            ).data.pagingReservationsResponse.toDomain()
        }
    }

    override suspend fun getReservationKeyword(keyword: String): NetworkResult<ReservationKeyword> {
        val body = GetReservationKeywordRequest(keyword = keyword)
        return handleApi { mainAPIService.getReservationKeyword(body = body).data.toDomain() }

    }

    override suspend fun getReservationSearch(
        keyword: String,
        page: Int,
        size: Int,
        sort: String?
    ): NetworkResult<PagingReservations> {
        val body = GetReservationKeywordRequest(keyword = keyword)
        return handleApi {
            mainAPIService.getReservationSearch(
                body = body,
                page = page,
                size = size,
                sort = sort
            ).data.pagingReservationsResponse.toDomain()
        }
    }

    override suspend fun postParticipation(id: Int): NetworkResult<Participation> {
        return handleApi { mainAPIService.postParticipation(id = id).data.toDomain() }
    }

    override suspend fun putParticipation(id: Int): NetworkResult<Participation> {
        return handleApi { mainAPIService.putParticipation(id = id).data.toDomain() }
    }

    override suspend fun deleteParticipation(id: Int): NetworkResult<Unit> {
        return handleApi { mainAPIService.deleteParticipation(id = id) }
    }

    override suspend fun getParticipation(id: Int): NetworkResult<Participation> {
        return handleApi { mainAPIService.getParticipation(id = id).data.toDomain() }
    }

    override suspend fun getUserReservation(
        page: Int,
        size: Int,
        sort: String?
    ): NetworkResult<PagingReservations> {
        return handleApi {
            mainAPIService.getUserReservation(
                page = page,
                size = size,
                sort = sort
            ).data.pagingReservationsResponse.toDomain()
        }
    }

    override suspend fun getUserParticipation(
        page: Int,
        size: Int,
        sort: String?
    ): NetworkResult<PagingReservations> {
        return handleApi {
            mainAPIService.getUserParticipation(
                page = page,
                size = size,
                sort = sort
            ).data.pagingReservationsResponse.toDomain()
        }
    }

    override suspend fun postOptionNight(): NetworkResult<Unit> {
        return handleApi { mainAPIService.postOptionNight() }
    }

    override suspend fun deleteOptionNight(): NetworkResult<Unit> {
        return handleApi { mainAPIService.deleteOptionNight() }
    }

    override suspend fun postOptionNew(): NetworkResult<Unit> {
        return handleApi { mainAPIService.postOptionNew() }
    }

    override suspend fun deleteOptionNew(): NetworkResult<Unit> {
        return handleApi { mainAPIService.deleteOptionNew() }
    }

    override suspend fun getOptions(): NetworkResult<Options> {
        return handleApi { mainAPIService.getOptions().data.toDomain() }
    }

    override suspend fun getNotifications(): NetworkResult<NotificationList> {
        return handleApi { mainAPIService.getNotifications().data.notifications.toDomain() }
    }

    override suspend fun postNotifications(
        group_id: Int,
        title: String,
        content: String,
        image_url: String
    ): NetworkResult<Unit> {
        val body = PostNotificationRequest(
            group_id = group_id,
            title = title,
            content = content,
            image_url = image_url
        )
        return handleApi { mainAPIService.postNotifications(body = body) }

    }

    override suspend fun postNotificationToken(
        deviceId: String,
        token: String
    ): NetworkResult<Unit> {
        val body = PostNotificationTokenRequest(deviceId = deviceId, token = token)
        return handleApi {
            mainAPIService.postNotificationToken(body = body)
        }
    }

    override suspend fun postFileToUrl(file: MultipartBody.Part): NetworkResult<ImageUrl> {
        return handleApi { mainAPIService.postFileToUrl(file = file).data.toDomain() }
    }

    override suspend fun deleteLeaveGroup(id: Int): NetworkResult<Group> {
        TODO("Not yet implemented")
    }

    override suspend fun getRecommendMessage(): NetworkResult<RecommendMessageList> {
        TODO("Not yet implemented")
    }

    override suspend fun getAppVersion(): NetworkResult<AppVersion> {
        TODO("Not yet implemented")
    }

    override suspend fun getProfiles(): NetworkResult<ProfileList> {
        return handleApi { mainAPIService.getProfiles().data.profiles.toDomain() }
    }

    override suspend fun getProfilesRandom(): NetworkResult<Profile> {
        return handleApi { mainAPIService.getProfilesRandom().data.toDomain() }
    }

    override suspend fun getAlarms(): NetworkResult<AlarmList> {
        return handleApi { mainAPIService.getAlarms().data.list.toDomain() }
    }

    override suspend fun getAlarmsCount(): NetworkResult<AlarmCount> {
        TODO("Not yet implemented")
    }

    override suspend fun postReportNotification(
        notification_id: Int,
        description: String,
        report_reason: String
    ): NetworkResult<ReportNotification> {
        val body =
            PostReportsNotificationRequest(description = description, report_reason = report_reason)
        return handleApi {
            mainAPIService.postReportsNotifications(
                notification_id = notification_id,
                body = body
            ).data.toDomain()
        }
    }


}