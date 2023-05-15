package com.sch.sch_taxi.ui.reservationdetail

import android.util.Log
import com.sch.domain.model.Reservation
import com.sch.domain.model.ReservationDetail
import com.sch.domain.model.Taxis
import com.sch.domain.onError
import com.sch.domain.onSuccess
import com.sch.domain.usecase.main.DeleteReservationUseCase
import com.sch.domain.usecase.main.GetOtherProfileUseCase
import com.sch.domain.usecase.main.GetParticipationUseCase
import com.sch.domain.usecase.main.GetReservationDetailUseCase
import com.sch.domain.usecase.main.GetUserParticipationUseCase
import com.sch.domain.usecase.main.PostParticipationUseCase
import com.sch.domain.usecase.main.PostReportsParticipationUseCase
import com.sch.sch_taxi.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Math.acos
import java.lang.Math.cos
import java.lang.Math.sin
import javax.inject.Inject
import kotlin.math.roundToLong

@HiltViewModel
class ReservationDetailViewModel @Inject constructor(
    private val getReservationDetailUseCase: GetReservationDetailUseCase,
    private val postParticipationUseCase: PostParticipationUseCase,
    private val getParticipationUseCase: GetParticipationUseCase,
    private val deleteReservationUseCase: DeleteReservationUseCase,
    private val getOtherProfileUseCase: GetOtherProfileUseCase,
    private val postReportsParticipationUseCase: PostReportsParticipationUseCase
) : BaseViewModel(), ReservationDetailActionHandler {

    private val TAG = "ReservationDetailViewModel"

    private val _navigationHandler: MutableSharedFlow<ReservationDetailNavigationAction> =
        MutableSharedFlow<ReservationDetailNavigationAction>()
    val navigationHandler: SharedFlow<ReservationDetailNavigationAction> =
        _navigationHandler.asSharedFlow()

    var reservationId = MutableStateFlow<Int>(-1)
    private val _notificationsEvent: MutableStateFlow<Taxis> =
        MutableStateFlow(Taxis(emptyList()))
    val notificationsEvent: StateFlow<Taxis> = _notificationsEvent
    var editTextReportEvent = MutableStateFlow<String>("")

    val reservesEvent: MutableStateFlow<ReservationDetail?> = MutableStateFlow(null)
    var startLatitude = MutableStateFlow<Double>(0.0)
    var startLongitude = MutableStateFlow<Double>(0.0)
    var destinationLatitude = MutableStateFlow<Double>(0.0)
    var destinationLongitude = MutableStateFlow<Double>(0.0)



    fun getReservationDetail() {
        baseViewModelScope.launch {
            getReservationDetailUseCase(reservationId = reservationId.value)
                .onSuccess {
                    reservesEvent.value = it
                    startLatitude.value = it.startLatitude
                    startLongitude.value = it.startLongitude
                    destinationLatitude.value = it.destinationLatitude
                    destinationLongitude.value = it.destinationLongitude


                }
                .onError {

                }

        }
    }

    fun getParticipation() {
        baseViewModelScope.launch {
            getParticipationUseCase(id = reservationId.value)
                .onSuccess { }
                .onError { }

        }
    }

    fun onClickedParticipation(seatPosition: String) {
        showLoading()
        baseViewModelScope.launch {
            postParticipationUseCase(id = reservationId.value, seatPosition = seatPosition)
                .onSuccess { }
                .onError { }
        }
        dismissLoading()
    }

    override fun onClickedBack() {
        baseViewModelScope.launch {
            _navigationHandler.emit(ReservationDetailNavigationAction.NavigateToBack)
        }
    }

    override fun onClickedReservationMoreBottomDialog() {
        baseViewModelScope.launch {
            _navigationHandler.emit(
                ReservationDetailNavigationAction.NavigateToReservationMoreBottomDialog(
                    0, 1
                )
            )
        }
    }

    fun onClickedReservationUpdateClicked() {
        baseViewModelScope.launch {
        }
    }

    fun onReservationDeleteClicked(reservationId: Int) {
        baseViewModelScope.launch {
            deleteReservationUseCase(reservationId = reservationId)
                .onSuccess {
                    _toastMessage.emit("게시글이 삭제되었습니다")
                }
                .onError { }

        }
    }

    fun onClickedUserReport(sendUserId: Int) {
        baseViewModelScope.launch {

        }
    }

    fun onClickedReport(reservationId: Int, reportReason: String) {
        baseViewModelScope.launch {

        }
    }

    // 좌표로 거리구하기
    private fun calDist(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Long {
        val EARTH_R = 6371000.0
        val rad = Math.PI / 180
        val radLat1 = rad * lat1
        val radLat2 = rad * lat2
        val radDist = rad * (lon1 - lon2)

        var distance = sin(radLat1) * sin(radLat2)
        distance += cos(radLat1) * cos(radLat2) * cos(radDist)
        val ret = EARTH_R * acos(distance)

        return ret.roundToLong() // 미터 단위
    }


}