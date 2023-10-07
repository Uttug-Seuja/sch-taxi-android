package com.sch.sch_taxi.ui.chatroom

import android.annotation.SuppressLint
import android.util.Log
import androidx.paging.PagingData
import androidx.paging.TerminalSeparatorType
import androidx.paging.cachedIn
import androidx.paging.insertFooterItem
import androidx.paging.insertHeaderItem
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.sch.data.api.ApiClient
import com.sch.domain.model.Chat
import com.sch.domain.usecase.main.PostChatUseCase
import com.sch.sch_taxi.base.BaseViewModel
import com.sch.sch_taxi.di.PresentationApplication
import com.sch.sch_taxi.ui.chatroom.adapter.createChatRoomPager
import com.sch.sch_taxi.ui.chatroom.model.SocketMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompHeader
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    private val postChatUseCase: PostChatUseCase,
) : BaseViewModel(), ChatRoomActionHandler {

    private val TAG = "ChatViewModel"

    var reservationId = MutableStateFlow<Int>(-1)
    var participationId = MutableStateFlow<Int>(-1)
    var myParticipationId = MutableStateFlow<Int>(-1)

    private val _navigationHandler: MutableSharedFlow<ChatRoomNavigationAction> =
        MutableSharedFlow<ChatRoomNavigationAction>()
    val navigationHandler: SharedFlow<ChatRoomNavigationAction> = _navigationHandler.asSharedFlow()

    private val _chatRoomEvent: MutableStateFlow<PagingData<Chat>> =
        MutableStateFlow(PagingData.empty())
    var chatRoomEvent: Flow<PagingData<Chat>> = _chatRoomEvent

    val message = MutableStateFlow("")
    val writer = MutableStateFlow("")
    val cursor = MutableStateFlow("")
    val userId = MutableStateFlow(0)

    var editMessage: MutableStateFlow<String> = MutableStateFlow("")
    var isEditMessageSend: MutableStateFlow<Boolean> = MutableStateFlow(false)


    private val url = "ws" + ApiClient.BASE_URL.substring(4) + "stomp/chat"
    private val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)

    val newMessageCheck = MutableStateFlow(0)

    fun postChat() {
        chatRoomEvent = createChatRoomPager(
            postChatUseCase = postChatUseCase, chatRoomViewModel = this
        ).flow.cachedIn(baseViewModelScope)
    }


    @SuppressLint("CheckResult")
    fun runStomp() {
        baseViewModelScope.launch {

            showLoading()

            stompClient.lifecycle().subscribe { lifecycleEvent ->
                when (lifecycleEvent.type) {
                    LifecycleEvent.Type.OPENED -> {
                        Log.d("ttt", "OPEND !!")
                    }

                    LifecycleEvent.Type.CLOSED -> {
                        Log.d("ttt", "CLOSED !!")

                    }

                    LifecycleEvent.Type.ERROR -> {
                        Log.d("ttt", "ERROR !!")
                        Log.d("ttt", "CONNECT ERROR" + lifecycleEvent.exception.toString())
                    }

                    else -> {
                        Log.d("ttt", "ELSE" + lifecycleEvent.message)
                    }
                }
            }

            val headerList = arrayListOf<StompHeader>()
            headerList.add(
                StompHeader(
                    "Authorization",
                    "Bearer " + PresentationApplication.sSharedPreferences.getString(
                        "accessToken", null
                    )
                )
            )

            stompClient.connect(headerList)

            kotlinx.coroutines.delay(500)
            dismissLoading()
            Log.d("ttt isConnected", stompClient.isConnected.toString())

            val subscribeHeader = arrayListOf<StompHeader>()
            subscribeHeader.add(
                StompHeader(
                    "Authorization",
                    "Bearer " + PresentationApplication.sSharedPreferences.getString(
                        "accessToken", null
                    )
                )
            )
            subscribeHeader.add(
                StompHeader(
                    "simpDestination", "/sub/chat/room/${reservationId.value}"
                )
            )
            subscribeHeader.add(StompHeader("simpSessionId", "asdasdfdfd"))


            stompClient.topic("/sub/chat/room/${reservationId.value}", subscribeHeader)
                .subscribe { topicMessage ->
                    val parser = JsonParser()
                    val obj: String = parser.parse(topicMessage.payload).toString()
                    val data = Gson().fromJson(obj, SocketMessage::class.java)


                    val newChatMessage = Chat(
                        reservationId = data.roomId.toInt(),
                        participationId = data.participationId,
                        userId = data.userId,
                        writer = data.writer,
                        message = data.message,
                        createdAt = data.createdAt,
                        profilePath = data.profilePath,
                        isend = data.participationId == myParticipationId.value
                    )

                    Log.d("ttt", "ㅗㅑㅠ야")

                    chatRoomEvent = chatRoomEvent.map { pagingData ->
                        Log.d("ttt", "ㅗㅑ3야")

                        pagingData.insertHeaderItem(
                            TerminalSeparatorType.FULLY_COMPLETE, item = newChatMessage
                        )
                    }

                    newMessageCheck.value += 1
                }
        }
    }

    fun disconnect() {
        stompClient.disconnect()
    }

    fun send() {
        val data = JSONObject()
        data.put("roomId", reservationId.value.toString())
        data.put("message", editMessage.value)
        data.put(
            "accessToken", "Bearer " + PresentationApplication.sSharedPreferences.getString(
                "accessToken", null
            )
        )

        stompClient.send("/pub/chat/message", data.toString()).subscribe()
        editMessage.value = ""
        isEditMessageSend.value = true
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