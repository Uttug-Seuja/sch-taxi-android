package com.sch.data.model

import com.sch.data.model.local.SearchHistoryEntity
import com.sch.domain.model.SearchHistory
import com.sch.domain.model.SearchHistoryList

//import com.o2.data.model.response.PagingGroupList
//import com.depromeet.domain.model.GroupList
//import com.depromeet.domain.model.NotificationContent
//import com.depromeet.domain.model.NotificationListContent
//
//fun PagingGroupList.toDomain() : GroupList {
//    return GroupList(
//        groupContent = groupContent,
//        last = last
//    )
//}
//
//fun PagingNotifications.toDomain() : NotificationContent {
//    return NotificationContent(
//        content = content,
//        last = last
//    )
//}
//
//fun PagingNotificationList.toDomain() : NotificationListContent {
//    return NotificationListContent(
////        groups = groups,
//        reservations = reservations,
//        notifications = notifications,
//    )
//}

fun SearchHistory.toData(): SearchHistoryEntity {
    return SearchHistoryEntity(
        title = this.title
    )
}

//fun List<SearchHistoryEntity>.toDomain(): List<SearchHistory> {
//    return map { SearchHistory(
//        searchHistoryIdx = it.searchHistoryIdx,
//        title = it.title
//    ) }
//}

fun List<SearchHistoryEntity>.toDomain(): SearchHistoryList {
    return SearchHistoryList(
        map {
            SearchHistory(
                searchHistoryIdx = it.searchHistoryIdx,
                title = it.title
            )
        }
    )
}

fun SearchHistoryEntity.toDomain(): SearchHistory {
    return SearchHistory(
        searchHistoryIdx = this.searchHistoryIdx,
        title = this.title
    )
}