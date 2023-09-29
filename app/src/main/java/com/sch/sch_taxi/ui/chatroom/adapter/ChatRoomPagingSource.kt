package com.sch.sch_taxi.ui.chatroom.adapter

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sch.domain.fold
import com.sch.domain.model.Chat
import com.sch.domain.usecase.main.PostChatUseCase
import com.sch.sch_taxi.ui.chatroom.ChatRoomViewModel

fun createChatRoomPager(
    postChatUseCase: PostChatUseCase,
    chatRoomViewModel: ChatRoomViewModel,
): Pager<Int, Chat> = Pager(
    config = PagingConfig(pageSize = 10, initialLoadSize = 10, enablePlaceholders = true),
    initialKey = 0,
    pagingSourceFactory = {
        ChatRoomPagingSource(
            postChatUseCase = postChatUseCase,
            chatRoomViewModel = chatRoomViewModel
        )
    }
)

class ChatRoomPagingSource(
    private val postChatUseCase: PostChatUseCase,
    private val chatRoomViewModel: ChatRoomViewModel,
) : PagingSource<Int, Chat>() {

    override fun getRefreshKey(state: PagingState<Int, Chat>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Chat> {
        val pageIndex = params.key ?: 0


        Log.d("ttt participationId", chatRoomViewModel.participationId.value.toString())
        Log.d("ttt reservationId", chatRoomViewModel.reservationId.value.toString())

        Log.d("ttt message", chatRoomViewModel.message.value)
        Log.d("ttt writer", chatRoomViewModel.writer.value)
        Log.d("ttt cursor", chatRoomViewModel.cursor.value)
        Log.d("ttt userId", chatRoomViewModel.userId.value.toString())

        val result = postChatUseCase(
            participationId = chatRoomViewModel.participationId.value,
            reservationId = chatRoomViewModel.reservationId.value,
            message = chatRoomViewModel.message.value,
            writer = chatRoomViewModel.writer.value,
            cursor = chatRoomViewModel.cursor.value,
            userId = chatRoomViewModel.userId.value,
        )


        return result.fold(
            onSuccess = { contents ->
                LoadResult.Page(
                    data = contents.chatPagingResponseDtoList,
                    prevKey = null,
                    nextKey = if (contents.chatPagingResponseDtoList.lastOrNull() != null) {
                        Log.d("Ttt pageIndex", pageIndex.toString())

                        Log.d("Ttt last", contents.chatPagingResponseDtoList.last().message)

                        chatRoomViewModel.myParticipationId.value = contents.myParticipationId

                        chatRoomViewModel.message.value =
                            contents.chatPagingResponseDtoList.last().message
                        chatRoomViewModel.writer.value =
                            contents.chatPagingResponseDtoList.last().writer
                        chatRoomViewModel.cursor.value =
                            contents.chatPagingResponseDtoList.last().createdAt
                        chatRoomViewModel.userId.value =
                            contents.chatPagingResponseDtoList.last().userId
                        chatRoomViewModel.participationId.value =
                            contents.chatPagingResponseDtoList.last().participationId

                        pageIndex + 1
                    } else null
                )
            },
            onError = { e -> LoadResult.Error(e) }
        )
    }

}