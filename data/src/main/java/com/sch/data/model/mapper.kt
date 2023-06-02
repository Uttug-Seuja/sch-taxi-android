package com.sch.data.model

import com.sch.data.model.local.SearchHistoryEntity
import com.sch.data.model.remote.response.*
import com.sch.domain.model.*

fun SearchHistory.toData(): SearchHistoryEntity {
    return SearchHistoryEntity(
        title = this.title
    )
}

fun LoginResponse.toDomain(): Token {
    return Token(
        accessToken = this.accessToken, refreshToken = this.refreshToken
    )
}

fun IsRegisteredResponse.toDomain(): IsRegistered {
    return IsRegistered(
        isRegistered = this.isRegistered
    )
}

fun UserInfoResponse.toDomain(): UserInfo {
    return UserInfo(
        userId = userId,
        gender = gender,
        name = name,
        email = email,
        profilePath = profilePath,
        currentTemperature = currentTemperature,
        temperatureImage = temperatureImage

    )
}

fun HostInfoResponse.toDomain(): HostInfo {
    return HostInfo(
        userId = userId,
        gender = gender,
        name = name,
        email = email,
        profilePath = profilePath,
    )
}

fun AssetRandomResponse.toDomain(): AssetRandom {
    return AssetRandom(
        url = url
    )
}

fun List<SearchHistoryEntity>.toDomain(): SearchHistoryList {
    return SearchHistoryList(map {
        SearchHistory(
            searchHistoryIdx = it.searchHistoryIdx, title = it.title
        )
    })
}

fun SearchHistoryEntity.toDomain(): SearchHistory {
    return SearchHistory(
        searchHistoryIdx = this.searchHistoryIdx, title = this.title
    )
}

fun ReservationResponse.toDomain(): Reservation {
    return Reservation(
        reservationId = reservationId,
        title = title,
        startPoint = startPoint,
        destination = destination,
        departureDate = departureDate,
        gender = gender,
        passengerNum = passengerNum,
        currentNum = currentNum,
        reservationStatus = reservationStatus
    )
}

fun ReservationDetailResponse.toDomain(): ReservationDetail {
    return ReservationDetail(
        reservationId = reservationId,
        title = title,
        startPoint = startPoint,
        destination = destination,
        departureDate = departureDate,
        gender = gender,
        passengerNum = passengerNum,
        currentNum = currentNum,
        startLatitude = startLatitude,
        startLongitude = startLongitude,
        destinationLatitude = destinationLatitude,
        destinationLongitude = destinationLongitude,
        iHost = ihost,
        hostInfo = hostInfo.toDomain(),
        reservationStatus = reservationStatus,
        createDate = createDate,
        lastModifyDate = lastModifyDate
    )
}

fun PagingReservationResponse.toDomain(): PagingReservation {
    return PagingReservation(content = this.content, last = this.last)
}

fun PagingReservationKeywordResponse.toDomain(): PagingReservationKeyword {
    return PagingReservationKeyword(content = this.content.toDomain(), last = this.last)
}

@JvmName("KeywordResponseToKeyword")
fun List<KeywordResponse>.toDomain(): List<Keyword> {
    return map {
        Keyword(it.keyword)
    }
}

fun ParticipationResponse.toDomain(): Participation {
    return Participation(
        participationInfoList = this.participationInfoList.toDomain(),
        iparticipation = this.iparticipation
    )
}

fun List<ParticipationInfoListResponse>.toDomain(): ParticipationInfoList {
    return ParticipationInfoList(
        map {
            ParticipationInfo(
                seatPosition = it.seatPosition,
                userInfo = it.userInfo.toDomain()
            )
        }

    )
}

@JvmName("ReservationResponseToReservation")
fun List<ReservationResponse>.toDomain(): List<Reservation> {
    return map {
        Reservation(
            reservationId = it.reservationId,
            title = it.title,
            startPoint = it.startPoint,
            destination = it.destination,
            departureDate = it.departureDate,
            gender = it.gender,
            passengerNum = it.passengerNum,
            currentNum = it.currentNum,
            reservationStatus = it.reservationStatus
        )
    }

}

fun OptionsResponse.toDomain(): Options {
    return Options(
        new_option = this.new_option,
        reaction_option = reaction_option,
        night_option = night_option
    )
}

fun List<NotificationResponse>.toDomain(): NotificationList {
    return NotificationList(
        map {
            Notification(
                content = it.content,
                created_date = it.created_date,
                image_url = it.image_url,
                notification_id = it.notification_id,
                send_user_id = it.send_user_id,
                title = it.title
            )

        }

    )
}

fun ImageUrlResponse.toDomain(): ImageUrl {
    return ImageUrl(imageUrl = this.imageUrl)
}

fun List<ProfileResponse>.toDomain(): ProfileList {
    return ProfileList(
        map {
            Profile(
                id = it.id,
                title = it.title,
                url = it.url
            )
        }
    )
}

fun ProfileResponse.toDomain(): Profile {
    return Profile(id = this.id, title = this.title, url = this.url)
}

fun List<AlarmResponse>.toDomain(): AlarmList {
    return AlarmList(
        map {
            Alarm(
                content = it.content,
                created_at = it.created_at,
                title = it.title,
                type = it.type,
                user_id = it.user_id,
                user_profile = it.user_profile
            )
        }
    )
}

fun ReportNotificationResponse.toDomain(): ReportNotification {
    return ReportNotification(
        id = this.id,
        notification_id = this.notification_id,
        report_reason = this.report_reason,
        description = this.description,
        defendant_id = this.defendant_id
    )
}