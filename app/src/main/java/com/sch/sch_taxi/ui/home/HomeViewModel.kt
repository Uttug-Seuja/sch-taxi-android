package com.sch.sch_taxi.ui.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sch.data.api.ApiClient
import com.sch.domain.model.Reservation
import com.sch.domain.model.Taxi
import com.sch.domain.model.Taxis
import com.sch.domain.onError
import com.sch.domain.onSuccess
import com.sch.domain.usecase.main.GetReservationUseCase
import com.sch.sch_taxi.base.BaseViewModel
import com.sch.sch_taxi.di.PresentationApplication.Companion.sSharedPreferences
import com.sch.sch_taxi.di.StompNetworkModule
import com.sch.sch_taxi.ui.home.adapter.createReservationPager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
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

        val headerList = arrayListOf<StompHeader>()
        headerList.add(
            StompHeader(
                "Authorization",
                "Bearer "+ sSharedPreferences.getString("accessToken", null)
            )
        )
        headerList.add(StompHeader("simpSessionId", "1"))
        stompClient.connect(headerList)

        stompClient.topic("/sub/chat/room/1").subscribe { topicMessage ->
            Log.i("message Recieve", topicMessage.payload)
        }

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
                    Log.d("ttt",  "ELSE" + lifecycleEvent.message)
                }
            }
        }

        val data = JSONObject()
        data.put("roomId", 1)
        data.put("message", "안녕하세요")

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