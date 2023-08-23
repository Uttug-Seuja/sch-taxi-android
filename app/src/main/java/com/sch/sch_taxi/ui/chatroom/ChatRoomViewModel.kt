package com.sch.sch_taxi.ui.chatroom

import android.util.Log
import com.sch.domain.model.Taxis
import com.sch.domain.onError
import com.sch.domain.onSuccess
import com.sch.domain.usecase.main.PostChatUseCase
import com.sch.sch_taxi.base.BaseViewModel
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

    private val _notificationsEvent: MutableStateFlow<Taxis> =
        MutableStateFlow(Taxis(emptyList()))
    val notificationsEvent: StateFlow<Taxis> = _notificationsEvent


    fun postChat() {
        baseViewModelScope.launch {
            postChatUseCase(
                reservationId = reservationId.value,
                message = "",
                writer = "",
                cursor = ""
            ).onSuccess {
                Log.d("ttt onSuccess postChat", it.toString())
            }.onError {
                Log.d("ttt onError postChat", it.toString())

            }

        }
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