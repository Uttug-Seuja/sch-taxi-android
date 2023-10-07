package com.sch.sch_taxi.ui.chatroom

import android.annotation.SuppressLint
import android.util.Log
import androidx.paging.PagingData
import androidx.paging.TerminalSeparatorType
import androidx.paging.cachedIn
import androidx.paging.insertHeaderItem
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.sch.data.api.ApiClient
import com.sch.data.model.remote.error.BadRequestException
import com.sch.domain.model.Chat
import com.sch.domain.model.Participation
import com.sch.domain.model.ReservationDetail
import com.sch.domain.onError
import com.sch.domain.onSuccess
import com.sch.domain.usecase.main.DeleteParticipationUseCase
import com.sch.domain.usecase.main.GetParticipationUseCase
import com.sch.domain.usecase.main.GetReservationDetailUseCase
import com.sch.domain.usecase.main.PatchParticipationUseCase
import com.sch.domain.usecase.main.PostChatUseCase
import com.sch.domain.usecase.main.PostReportsParticipationUseCase
import com.sch.sch_taxi.base.BaseViewModel
import com.sch.sch_taxi.di.PresentationApplication
import com.sch.sch_taxi.ui.chatroom.adapter.createChatRoomPager
import com.sch.sch_taxi.ui.chatroom.model.SocketMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompHeader
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    private val postChatUseCase: PostChatUseCase,
    private val getReservationDetailUseCase: GetReservationDetailUseCase,
    private val getParticipationUseCase: GetParticipationUseCase,
    private val patchParticipationUseCase: PatchParticipationUseCase,
    private val postReportsParticipationUseCase: PostReportsParticipationUseCase,
    private val deleteParticipationUseCase: DeleteParticipationUseCase
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

    val participationEvent: MutableStateFlow<Participation?> = MutableStateFlow(null)
    val reservesEvent: MutableStateFlow<ReservationDetail?> = MutableStateFlow(null)


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

    fun getReservationDetail() {
        baseViewModelScope.launch {
            showLoading()
            getReservationDetailUseCase(reservationId = reservationId.value).onSuccess {
                reservesEvent.value = it
            }.onError {
                Log.d("ttt getReservationDetailUseCase onError", it.toString())

            }
            dismissLoading()

        }
    }

    fun getParticipation() {
        baseViewModelScope.launch {
            showLoading()
            getParticipationUseCase(id = reservationId.value).onSuccess {
                participationEvent.value = it
                if (participationEvent.value!!.iparticipation) {
                    reservesEvent.value!!.reservationStatus = "CHATTING"
                }

            }.onError {
                Log.d("ttt onError", it.toString())

            }
            dismissLoading()

        }
    }

    fun onClickedPatchParticipation(seatPosition: String) {
        Log.d("ttt it", reservationId.value.toString())
        Log.d("ttt it", seatPosition.toString())

        baseViewModelScope.launch {
            showLoading()
            patchParticipationUseCase(
                participationId = reservationId.value, seatPosition = seatPosition
            ).onSuccess {
                getParticipation()
                _toastMessage.emit("수정되었습니다")

            }.onError {
                Log.d("ttt it", it.toString())
                when (it) {
                    is BadRequestException -> baseViewModelScope.launch {
                        _toastMessage.emit("잘못된 성별입니다")
                    }

                    else -> {
                        _toastMessage.emit("시스템 에러가 발생 하였습니다")
                    }
                }
            }
            dismissLoading()
        }
    }

    fun onClickedReservationMoreBottomDialog() {
        baseViewModelScope.launch {
//            _navigationHandler.emit(ChatRoomNavigationAction.NavigateToBack)
            Log.d("Ttt reservationId.value", reservationId.value.toString())
            Log.d("Ttt reservesEvent", reservesEvent.value.toString())

            _navigationHandler.emit(
                ChatRoomNavigationAction.NavigateToReservationMoreBottomDialog(
                    reservationId = reservationId.value, reservesEvent.value!!.hostInfo.userId
                )
            )
        }
    }

    override fun onClickedSelectSeatBottomDialog() {

        baseViewModelScope.launch {
            _navigationHandler.emit(
                ChatRoomNavigationAction.NavigateToSelectSeatBottomDialog
            )
        }
    }

    fun onClickedUserReport(sendUserId: Int) {
        baseViewModelScope.launch {

        }
    }

    fun onClickedReport(reservationId: Int, reportReason: String) {
        baseViewModelScope.launch {
            showLoading()
            postReportsParticipationUseCase(
                participationId = reservationId,
                reportReason = reportReason,
                reportType = reportReason

            ).onSuccess { }.onError { }
            dismissLoading()

        }
    }

    override fun onClickedUserProfile(userId: Int) {
         baseViewModelScope.launch {
            _navigationHandler.emit(ChatRoomNavigationAction.NavigateToUserProfile(userId = userId))

        }
    }

    fun onClickedDeleteParticipation() {
        baseViewModelScope.launch {
            deleteParticipationUseCase(id = reservationId.value).onSuccess {
                _navigationHandler.emit(ChatRoomNavigationAction.NavigateToBack)
            }.onError {
                Log.d("ttt", it.toString())
            }

        }

    }
}