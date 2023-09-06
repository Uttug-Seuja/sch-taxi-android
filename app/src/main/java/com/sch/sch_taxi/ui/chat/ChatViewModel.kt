package com.sch.sch_taxi.ui.chat

import android.util.Log
import com.sch.domain.model.ChatRoom
import com.sch.domain.onError
import com.sch.domain.onSuccess
import com.sch.domain.usecase.main.GetChatRoomUseCase
import com.sch.sch_taxi.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getChatRoomUseCase: GetChatRoomUseCase,
) : BaseViewModel(), ChatActionHandler {

    private val TAG = "ChatViewModel"

    private val _navigationHandler: MutableSharedFlow<ChatNavigationAction> =
        MutableSharedFlow<ChatNavigationAction>()
    val navigationHandler: SharedFlow<ChatNavigationAction> =
        _navigationHandler.asSharedFlow()

    private val _chatRoomEvent: MutableStateFlow<List<ChatRoom>> = MutableStateFlow(emptyList())
    val chatRoomEvent: StateFlow<List<ChatRoom>> = _chatRoomEvent

    init {
        getChatRoom()
    }

    fun getChatRoom() {
        baseViewModelScope.launch {
            getChatRoomUseCase().onSuccess {
                _chatRoomEvent.value = it

            }.onError {
                Log.d("Ttt", it.toString())
            }
        }

    }

    override fun onClickedBack() {
        baseViewModelScope.launch {
            _navigationHandler.emit(ChatNavigationAction.NavigateToBack)
        }
    }

    override fun onClickedNotification() {
        baseViewModelScope.launch {
            _navigationHandler.emit(ChatNavigationAction.NavigateToBack)
        }
    }

    override fun onClickedChatRoom(reservationId : Int) {
        baseViewModelScope.launch {
            _navigationHandler.emit(ChatNavigationAction.NavigateToChattingRoom(reservationId = reservationId))
        }
    }
}