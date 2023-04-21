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

fun UserProfileResponse.toDomain(): UserProfile {
    return UserProfile(
        email = email, id = id, nickname = nickname, profilePath = profilePath
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
        id = this.id,
        title = this.title,
        reservationTime = this.reservationTime,
        startingPlace = this.startingPlace,
        destination = this.destination,
        sex = this.sex,
        reservationStatus = this.reservationStatus
    )
}

fun ReservationDetailResponse.toDomain(): ReservationDetail {
    return ReservationDetail(
        id = id,
        title = title,
        reserveDate = reserveDate,
        reserveTime = reserveTime,
        startingPlace = startingPlace,
        destination = destination,
        sex = sex,
        userSex = userSex,
        createdAt = createdAt,
        currentNum = currentNum,
        passengerNum = passengerNum,
        challengeWord = challengeWord,
        countersignWord = countersignWord,
        startLatitude = startLatitude,
        startLongitude = startLongitude,
        finishLatitude = finishLatitude,
        finishLongitude = finishLongitude,
        reservationStatus = reservationStatus,
        userUid = userUid,
        name = name,
        schoolNum = schoolNum
    )
}

fun PagingReservationsResponse.toDomain(): PagingReservations {
    return PagingReservations(content = this.content, last = this.last)
}

fun ReservationKeywordResponse.toDomain(): ReservationKeyword {
    return ReservationKeyword(keywordId = this.keywordId, keywordTitle = this.keywordTitle)
}

fun ParticipationResponse.toDomain(): Participation {
    return Participation(reservationId = this.reservationId, isParticipation = isParticipation)
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
    return ImageUrl(image_url = this.image_url)
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