package com.sch.sch_taxi.ui.chatroom

import android.util.Log
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sch.domain.model.Chat
import com.sch.domain.model.Reservation
import com.sch.domain.model.Taxis
import com.sch.domain.onError
import com.sch.domain.onSuccess
import com.sch.domain.usecase.main.PostChatUseCase
import com.sch.sch_taxi.base.BaseViewModel
import com.sch.sch_taxi.ui.chatroom.adapter.createChatRoomPager
import com.sch.sch_taxi.ui.home.adapter.createReservationPager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    private val postChatUseCase: PostChatUseCase
) : BaseViewModel(), ChatRoomActionHandler {

    private val TAG = "ChatViewModel"

    var reservationId = MutableStateFlow<Int>(-1)

    private val _navigationHandler: MutableSharedFlow<ChatRoomNavigationAction> =
        MutableSharedFlow<ChatRoomNavigationAction>()
    val navigationHandler: SharedFlow<ChatRoomNavigationAction> =
        _navigationHandler.asSharedFlow()

    var chatRoomEvent: Flow<PagingData<Chat>> = emptyFlow()
    private val _tempChatRoomEvent: MutableStateFlow<List<Chat>> =
        MutableStateFlow(listOf())
    val tempChatRoomEvent: StateFlow<List<Chat>> = _tempChatRoomEvent

    val message = MutableStateFlow("")
    val writer = MutableStateFlow("")
    val cursor = MutableStateFlow("")
    val userId = MutableStateFlow(0)

    fun postChat() {
        chatRoomEvent =
            createChatRoomPager(
                reservationId = reservationId.value,
                postChatUseCase = postChatUseCase,
                chatRoomViewModel = this
                ).flow.cachedIn(
                baseViewModelScope
            )
    }

    override fun onClickedBack() {
        baseViewModelScope.launch {
            _navigationHandler.emit(ChatRoomNavigationAction.NavigateToBack)
        }
    }

    override fun onClickedNotification() {
        baseViewModelScope.launch {
            _navigationHandler.emit(ChatRoomNavigationAction.NavigateToBack)
        }
    }
}