package com.sch.sch_taxi.ui.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.google.gson.JsonParser
import com.sch.data.api.ApiClient
import com.sch.domain.model.Reservation
import com.sch.domain.usecase.main.GetReservationUseCase
import com.sch.sch_taxi.base.BaseViewModel
import com.sch.sch_taxi.di.PresentationApplication.Companion.sSharedPreferences
import com.sch.sch_taxi.ui.home.adapter.createReservationPager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompHeader
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getReservationUseCase: GetReservationUseCase
) : BaseViewModel(), HomeActionHandler {

    private val TAG = "HomeViewModel"

    private val _navigationHandler: MutableSharedFlow<HomeNavigationAction> =
        MutableSharedFlow<HomeNavigationAction>()
    val navigationHandler: SharedFlow<HomeNavigationAction> =
        _navigationHandler.asSharedFlow()

    var reservationEvent: Flow<PagingData<Reservation>> = emptyFlow()

    private val url = "ws" + ApiClient.BASE_URL.substring(4) + "stomp/chat"

    private val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)

    init {
        getReservation()

        Log.d("ttt", url.toString())
        baseViewModelScope.launch {
            runStomp()
        }

    }

    @SuppressLint("CheckResult")
    fun runStomp() {


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
                "Bearer " + sSharedPreferences.getString("accessToken", null)
            )
        )
        headerList.add(StompHeader("simpDestination", "/sub/chat/room/3"))
        headerList.add(StompHeader("simpSessionId", "asdasdfdfd"))

        stompClient.connect(headerList)

//        Thread.sleep(10000)
        Log.d("ttt isConnected", stompClient.isConnected.toString())

        Log.d("ttt subscribe", "/sub/chat/room/3")



//        Thread.sleep(10000)

        stompClient.topic("/sub/chat/room/3").subscribe { topicMessage ->
            val parser = JsonParser()
            val obj: Any = parser.parse(topicMessage.payload)
            Log.d("ttt message RabbitPathMatcherecieve", obj.toString())
        }

//        stompClient.topic("/sub/chat/room/2").subscribe()



        val data = JSONObject()
        data.put("roomId", "3")
        data.put("message", "하이 병신들")
        data.put("uid", "11")

        Log.d("ttt send", "/pub/chat/message")
        stompClient.send("/pub/chat/message", data.toString()).subscribe()
        stompClient.send("/pub/chat/message", data.toString()).subscribe()
        stompClient.send("/pub/chat/message", data.toString()).subscribe()
        stompClient.send("/pub/chat/message", data.toString()).subscribe()
        stompClient.send("/pub/chat/message", data.toString()).subscribe()
        stompClient.send("/pub/chat/message", data.toString()).subscribe()

    }

    fun getReservation() {
        reservationEvent =
            createReservationPager(getReservationUseCase = getReservationUseCase).flow.cachedIn(
                baseViewModelScope
            )
    }

    override fun onClickedSearch() {
        baseViewModelScope.launch {
            _navigationHandler.emit(HomeNavigationAction.NavigateToSearch)
        }
    }

    override fun onClickedTaxiDetail(reservationId: Int) {
        baseViewModelScope.launch {
            _navigationHandler.emit(HomeNavigationAction.NavigateToTaxiDetail(reservationId = reservationId))
        }
    }

    override fun onClickedNotifications() {
        baseViewModelScope.launch {
            _navigationHandler.emit(HomeNavigationAction.NavigateToNotifications)
        }
    }

    override fun onClickedTaxiCreate() {
        baseViewModelScope.launch {
            _navigationHandler.emit(HomeNavigationAction.NavigateToTaxiCreate)
        }
    }
}