package com.sch.sch_taxi.ui.chatroom.adapter

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sch.domain.fold
import com.sch.domain.model.Chat
import com.sch.domain.onError
import com.sch.domain.onSuccess
import com.sch.domain.usecase.main.PostChatUseCase
import com.sch.sch_taxi.ui.chatroom.ChatRoomViewModel

fun createChatRoomPager(
    reservationId: Int,
    postChatUseCase: PostChatUseCase,
    chatRoomViewModel: ChatRoomViewModel
): Pager<Int, Chat> = Pager(
    config = PagingConfig(pageSize = 10, initialLoadSize = 10, enablePlaceholders = true),
    initialKey = 0,
    pagingSourceFactory = {
        ChatRoomPagingSource(
            reservationId = reservationId,
            postChatUseCase = postChatUseCase,
            chatRoomViewModel = chatRoomViewModel
        )
    }
)

class ChatRoomPagingSource(
    private val reservationId: Int,
    private val postChatUseCase: PostChatUseCase,
    private val chatRoomViewModel: ChatRoomViewModel
) : PagingSource<Int, Chat>() {

    override fun getRefreshKey(state: PagingState<Int, Chat>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Chat> {
        val pageIndex = params.key ?: 0


        val result = postChatUseCase(
            reservationId = reservationId,
            message = chatRoomViewModel.message.value,
            writer = chatRoomViewModel.writer.value,
            cursor = chatRoomViewModel.cursor.value,
            userId = chatRoomViewModel.userId.value
        ).onSuccess {
            Log.d("ttt message", chatRoomViewModel.message.value)
            Log.d("ttt writer", chatRoomViewModel.writer.value)
            Log.d("ttt cursor", chatRoomViewModel.cursor.value)

            Log.d("ttt s", it.toString())
        }.onError {
            Log.d("ttt e", it.toString())
        }

        return result.fold(
            onSuccess = { contents ->
                LoadResult.Page(
                    data = contents.chatPagingResponseDtoList,
                    prevKey = null,
                    nextKey = if (contents.chatPagingResponseDtoList.lastOrNull() != null) {
                        Log.d("Ttt pageIndex", pageIndex.toString())

                        Log.d("Ttt last", contents.chatPagingResponseDtoList.last().message)
                        chatRoomViewModel.message.value = contents.chatPagingResponseDtoList.last().message
                        chatRoomViewModel.writer.value = contents.chatPagingResponseDtoList.last().writer
                        chatRoomViewModel.cursor.value = contents.chatPagingResponseDtoList.last().createdAt
                        chatRoomViewModel.userId.value = contents.chatPagingResponseDtoList.last().userId

                        pageIndex + 1
                    } else null
                )
            },
            onError = { e -> LoadResult.Error(e) }
        )
    }

}