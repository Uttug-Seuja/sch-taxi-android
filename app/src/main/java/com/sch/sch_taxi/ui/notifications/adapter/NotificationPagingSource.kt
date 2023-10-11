package com.sch.sch_taxi.ui.notifications.adapter

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sch.domain.fold
import com.sch.domain.model.Notification
import com.sch.domain.usecase.main.GetNotificationUseCase

fun createNotificationPager(
    getNotificationUseCase: GetNotificationUseCase
): Pager<Int, Notification> = Pager(
    config = PagingConfig(pageSize = 10, initialLoadSize = 10, enablePlaceholders = true),
    initialKey = 0,
    pagingSourceFactory = {
        NotificationPagingSource(
            getNotificationUseCase = getNotificationUseCase
        )
    }
)

class NotificationPagingSource(
    private val getNotificationUseCase: GetNotificationUseCase
) : PagingSource<Int, Notification>() {

    override fun getRefreshKey(state: PagingState<Int, Notification>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Notification> {
        val pageIndex = params.key ?: 0
        val result = getNotificationUseCase(
            page = pageIndex,
            size = params.loadSize,
        )
        return result.fold(
            onSuccess = { contents ->
                LoadResult.Page(
                    data = contents.content,
                    prevKey = null,
                    nextKey = if (!contents.last) pageIndex + 1 else null
                )
            },
            onError = { e -> LoadResult.Error(e) }
        )
    }

}