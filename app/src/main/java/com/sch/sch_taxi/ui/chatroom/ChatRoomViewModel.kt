package com.sch.sch_taxi.ui.chatroom

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertHeaderItem
import androidx.paging.map
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.sch.data.api.ApiClient
import com.sch.domain.model.Chat
import com.sch.domain.model.Reservation
import com.sch.domain.model.Taxis
import com.sch.domain.onError
import com.sch.domain.onSuccess
import com.sch.domain.usecase.main.PostChatUseCase
import com.sch.sch_taxi.base.BaseViewModel
import com.sch.sch_taxi.di.PresentationApplication
import com.sch.sch_taxi.ui.chatroom.adapter.createChatRoomPager
import com.sch.sch_taxi.ui.chatroom.model.SocketMessage
import com.sch.sch_taxi.ui.home.adapter.createReservationPager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompHeader
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    private val postChatUseCase: PostChatUseCase
) : BaseViewModel(), ChatRoomActionHandler {

    private val TAG = "ChatViewModel"

    var reservationId = MutableStateFlow<Int>(-1)
    var participationId = MutableStateFlow<Int>(-1)
    var myParticipationId = MutableStateFlow<Int>(-1)

    private val _navigationHandler: MutableSharedFlow<ChatRoomNavigationAction> =
        MutableSharedFlow<ChatRoomNavigationAction>()
    val navigationHandler: SharedFlow<ChatRoomNavigationAction> =
        _navigationHandler.asSharedFlow()

    private val _chatRoomEvent: MutableStateFlow<PagingData<Chat>> =
        MutableStateFlow(PagingData.empty())
    var chatRoomEvent: Flow<PagingData<Chat>> = _chatRoomEvent

    val message = MutableStateFlow("")
    val writer = MutableStateFlow("")
    val cursor = MutableStateFlow("")
    val userId = MutableStateFlow(0)

    var editMessage: MutableStateFlow<String> = MutableStateFlow("")

    private val url = "ws" + ApiClient.BASE_URL.substring(4) + "stomp/chat"
    private val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)


    fun postChat() {
        chatRoomEvent =
            createChatRoomPager(
                postChatUseCase = postChatUseCase,
                chatRoomViewModel = this
            ).flow.cachedIn(
                baseViewModelScope
            )
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
                        "accessToken",
                        null
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
                        "accessToken",
                        null
                    )
                )
            )
            subscribeHeader.add(
                StompHeader(
                    "simpDestination",
                    "/sub/chat/room/${reservationId.value}"
                )
            )
            subscribeHeader.add(StompHeader("simpSessionId", "asdasdfdfd"))


            stompClient.topic("/sub/chat/room/${reservationId.value}", subscribeHeader)
                .subscribe { topicMessage ->
                    val parser = JsonParser()
                    val obj: String = parser.parse(topicMessage.payload).toString()
                    val data = Gson().fromJson(obj, SocketMessage::class.java)
                    Log.d("ttt message RabbitPathMatcherecieve", data.toString())

                    val currentPagingData = _chatRoomEvent.value
                    val updatedPagingData = currentPagingData.map { chat ->
                        if (chat.reservationId == data.roomId.toInt() && chat.participationId == data.participationId) {
                            // 기존 메시지와 신규 메시지를 비교하여 필요한 업데이트를 수행
                            chat.copy(
                                writer = data.writer,
                                message = data.message,
                                createdAt = data.createdAt,
                                profilePath = data.profilePath,
                                isend = participationId == myParticipationId
                            )
                        } else {
                            chat
                        }
                    }
//                    val updatedPagingData = currentPagingData.insertHeaderItem(
//                        item = Chat(
//                            reservationId = data.roomId.toInt(),
//                            participationId = data.participationId,
//                            userId = data.userId,
//                            writer = data.writer,
//                            message = data.message,
//                            createdAt = data.createdAt,
//                            profilePath = data.profilePath,
//                            isend = participationId == myParticipationId
//                        )
//                    )

                    _chatRoomEvent.value = updatedPagingData // 데이터를 업데이트
                    chatRoomEvent  = _chatRoomEvent


                }
        }
    }


//    {
//        stompClient.lifecycle().subscribe { lifecycleEvent ->
//            when (lifecycleEvent.type) {
//                LifecycleEvent.Type.OPENED -> {
//                    Log.d("ttt", "OPEND !!")
//                }
//
//                LifecycleEvent.Type.CLOSED -> {
//                    Log.d("ttt", "CLOSED !!")
//
//                }
//
//                LifecycleEvent.Type.ERROR -> {
//                    Log.d("ttt", "ERROR !!")
//                    Log.d("ttt", "CONNECT ERROR" + lifecycleEvent.exception.toString())
//                }
//
//                else -> {
//                    Log.d("ttt", "ELSE" + lifecycleEvent.message)
//                }
//            }
//        }
//
//        val connectHeader = arrayListOf<StompHeader>()
//        connectHeader.add(
//            StompHeader(
//                "Authorization",
//                "Bearer " + PresentationApplication.sSharedPreferences.getString(
//                    "accessToken",
//                    null
//                )
//            )
//        )
//        connectHeader.add(StompHeader("simpSessionId", "asdasdfdfd"))
//
//        stompClient.connect(connectHeader)
//
//        val subscribeHeader = arrayListOf<StompHeader>()
//        subscribeHeader.add(
//            StompHeader(
//                "Authorization",
//                "Bearer " + PresentationApplication.sSharedPreferences.getString(
//                    "accessToken",
//                    null
//                )
//            )
//        )
//        subscribeHeader.add(StompHeader("simpDestination", "/sub/chat/room/${reservationId.value}"))
//        subscribeHeader.add(StompHeader("simpSessionId", "asdasdfdfd"))
//
//        stompClient.topic("/sub/chat/room/${reservationId.value}", subscribeHeader)
//            .subscribe { topicMessage ->
//                val parser = JsonParser()
//                val obj: Any = parser.parse(topicMessage.payload)
//                Log.d("ttt message RabbitPathMatcherecieve", obj.toString())
//            }
//    }

    fun disconnect() {
        stompClient.disconnect()
    }

    fun send() {
        val data = JSONObject()
        data.put("roomId", reservationId.value.toString())
        data.put("message", editMessage.value)
        data.put(
            "accessToken", "Bearer " + PresentationApplication.sSharedPreferences.getString(
                "accessToken",
                null
            )
        )

        stompClient.send("/pub/chat/message", data.toString()).subscribe()
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