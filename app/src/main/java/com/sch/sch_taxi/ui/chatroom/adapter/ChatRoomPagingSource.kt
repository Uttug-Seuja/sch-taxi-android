package com.sch.sch_taxi.ui.chatroom.adapter

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sch.domain.fold
import com.sch.domain.model.Chat
import com.sch.domain.usecase.main.PostChatUseCase

fun createChatRoomPager(
    reservationId: Int,
    postChatUseCase: PostChatUseCase
): Pager<Int, Chat> = Pager(
    config = PagingConfig(pageSize = 10, initialLoadSize = 10, enablePlaceholders = true),
    initialKey = 0,
    pagingSourceFactory = {
        ChatRoomPagingSource(
            reservationId = reservationId,
            postChatUseCase = postChatUseCase
        )
    }
)

class ChatRoomPagingSource(
    private val reservationId: Int,
    private val postChatUseCase: PostChatUseCase
) : PagingSource<Int, Chat>() {

    override fun getRefreshKey(state: PagingState<Int, Chat>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Chat> {
        val pageIndex = params.key ?: 0

        var message = ""
        var writer = ""
        var cursor = ""

        val result = postChatUseCase(
            reservationId = reservationId,
            message = message,
            writer = writer,
            cursor = cursor
        )
        return result.fold(
            onSuccess = { contents ->
                LoadResult.Page(
                    data = contents.chatPagingResponseDtoList,
                    prevKey = null,
                    nextKey = if (contents.chatPagingResponseDtoList.lastOrNull() != null) {
                        message = contents.chatPagingResponseDtoList.last().message
                        writer = contents.chatPagingResponseDtoList.last().writer
                        cursor = contents.chatPagingResponseDtoList.last().createdAt

                        pageIndex + 1
                    } else null
                )
            },
            onError = { e -> LoadResult.Error(e) }
        )
    }

}