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

    val newMessage: MutableStateFlow<MutableList<Chat>> = MutableStateFlow(mutableListOf())

    private val url = "ws" + ApiClient.BASE_URL.substring(4) + "stomp/chat"
    private val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)

    lateinit var chatPager: Flow<PagingData<Chat>>

    val check = MutableStateFlow(0)

    fun postChat() {

        chatPager = createChatRoomPager(
            postChatUseCase = postChatUseCase, chatRoomViewModel = this
        ).flow.cachedIn(baseViewModelScope)

        chatRoomEvent = chatPager.map { pagingData ->
            Log.d("ttt", "hy")
            // 원래의 PagingData와 새로운 아이템을 합치는 작업을 수행
            val newItem = Chat(
                reservationId = 11,
                participationId = 22,
                userId = 11,
                writer = "data.writer",
                message = "data.message",
                createdAt = "2023/09/07 20:59:42.990",
                profilePath = "https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7C737421e7-e595-4349-9ecb-d2363e821463.jpeg",
                isend = participationId == myParticipationId
            )

            // 새로운 아이템을 PagingData에 추가
            val combinedPagingData = pagingData.insertHeaderItem(item = newItem)
            combinedPagingData
        }

//        chatRoomEvent =
//            createChatRoomPager(
//                postChatUseCase = postChatUseCase,
//                chatRoomViewModel = this
//            ).flow.cachedIn(
//                baseViewModelScope
//            ).combine(newItemsFlow) { pagingData, newItems ->
//                // Flow.combine을 사용하여 PagingData와 새로운 아이템들을 합칩니다.
//
//                pagingData
////                // 새로운 아이템들을 PagingData에 추가
////                var combinedPagingData = pagingData
////                newItems.forEach {
////                    combinedPagingData =
////                        pagingData.insertHeaderItem(TerminalSeparatorType.FULLY_COMPLETE, it)
////
////                }
////
////                combinedPagingData
//            }
//                .map { pagingData ->
//
//                baseViewModelScope.launch {
//                    newMessage.collectLatest {
//                        Log.d("ttt newMessage", newMessage.toString())
//                        it.fold(pagingData) { acc, item ->
//                            acc.insertHeaderItem(TerminalSeparatorType.FULLY_COMPLETE, item)
//                        }
//                    }
//                }
//
//
//                // 원래의 PagingData와 새로운 아이템을 합치는 작업을 수행
//                val newItem = Chat(
//                    reservationId = 11,
//                    participationId = 22,
//                    userId = 11,
//                    writer = "data.writer",
//                    message = "data.message",
//                    createdAt = "2023/09/07 20:59:42.990",
//                    profilePath = "https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7C737421e7-e595-4349-9ecb-d2363e821463.jpeg",
//                    isend = participationId == myParticipationId
//                )
//
//                val headerList = listOf(newItem, newItem, newItem)
//
//                headerList.fold(pagingData) { acc, item ->
//                    acc.insertHeaderItem(TerminalSeparatorType.FULLY_COMPLETE, item)
//                }
//            }

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
                        isend = participationId != myParticipationId
                    )

                    newMessage.value =
                        (newMessage.value + mutableListOf(newChatMessage)) as MutableList<Chat>

                    Log.d("ttt", "ㅗㅑㅠ야")

                    chatRoomEvent = chatRoomEvent.map { pagingData ->
                        Log.d("ttt", "ㅗㅑ3야")

                        pagingData.insertHeaderItem(
                            TerminalSeparatorType.FULLY_COMPLETE,
                            item = newChatMessage
                        )
                    }

                    check.value = 1
//                    _chatRoomEvent.value = updatedPagingData
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