package com.sch.sch_taxi.ui.reservationdetail

import com.sch.data.model.remote.error.BadRequestException
import com.sch.domain.model.Participation
import com.sch.domain.model.ReservationDetail
import com.sch.domain.model.Taxis
import com.sch.domain.onError
import com.sch.domain.onSuccess
import com.sch.domain.usecase.main.DeleteReservationUseCase
import com.sch.domain.usecase.main.GetParticipationUseCase
import com.sch.domain.usecase.main.GetReservationDetailUseCase
import com.sch.domain.usecase.main.PatchParticipationUseCase
import com.sch.domain.usecase.main.PostParticipationUseCase
import com.sch.domain.usecase.main.PostReportsParticipationUseCase
import com.sch.sch_taxi.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservationDetailViewModel @Inject constructor(
    private val getReservationDetailUseCase: GetReservationDetailUseCase,
    private val postParticipationUseCase: PostParticipationUseCase,
    private val getParticipationUseCase: GetParticipationUseCase,
    private val patchParticipationUseCase: PatchParticipationUseCase,
    private val deleteReservationUseCase: DeleteReservationUseCase,
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

    val participationEvent: MutableStateFlow<Participation?> = MutableStateFlow(null)

    fun getReservationDetail() {
        baseViewModelScope.launch {
            showLoading()
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
            dismissLoading()

        }
    }

    fun getParticipation() {
        baseViewModelScope.launch {
            showLoading()
            getParticipationUseCase(id = reservationId.value)
                .onSuccess {
                    participationEvent.value = it
                    if (participationEvent.value!!.iparticipation) {
                        reservesEvent.value!!.reservationStatus = "CHATTING"
                    }

                }
                .onError {

                }
            dismissLoading()

        }
    }

    fun onClickedParticipation(seatPosition: String) {
        baseViewModelScope.launch {
            showLoading()
            postParticipationUseCase(
                reservationId = reservationId.value,
                seatPosition = seatPosition
            )
                .onSuccess {
                    getParticipation()
                    _toastMessage.emit("신청되었습니다")

                }
                .onError {
                    when (it) {
                        is BadRequestException -> baseViewModelScope.launch {
                            _toastMessage.emit("잘못된 성별입니다")
                        }

                        else -> {
                            _toastMessage.emit("시스템 에러가 발생 하였습니다")
                        }
                    }
                }
        }
        dismissLoading()
    }

    fun onClickedPatchParticipation(seatPosition: String) {
        baseViewModelScope.launch {
            showLoading()
            patchParticipationUseCase(
                participationId = reservationId.value,
                seatPosition = seatPosition
            )
                .onSuccess {
                    getParticipation()
                    _toastMessage.emit("수정되었습니다")

                }
                .onError {
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

    override fun onClickedBack() {
        baseViewModelScope.launch {
            _navigationHandler.emit(ReservationDetailNavigationAction.NavigateToBack)
        }
    }

    override fun onClickedReservationMoreBottomDialog() {
        baseViewModelScope.launch {
            _navigationHandler.emit(
                ReservationDetailNavigationAction.NavigateToReservationMoreBottomDialog(
                    reservationId = reservationId.value, reservesEvent.value!!.hostInfo.userId
                )
            )
        }
    }

    override fun onClickedReservationUpdate() {
        baseViewModelScope.launch {
            _navigationHandler.emit(
                ReservationDetailNavigationAction.NavigateToReservationUpdate(
                    reservationId = reservationId.value,
                )
            )
        }
    }

    override fun onClickedSelectSeatBottomDialog() {

        baseViewModelScope.launch {
            if (reservesEvent.value!!.reservationStatus == "CHATTING") {
                _navigationHandler.emit(ReservationDetailNavigationAction.NavigateToChatRoom(reservationId = reservationId.value))

            } else {
                _navigationHandler.emit(
                    ReservationDetailNavigationAction.NavigateToSelectSeatBottomDialog
                )
            }
        }
    }

    fun onClickedReservationUpdateClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(
                ReservationDetailNavigationAction.NavigateToReservationUpdate(reservationId = reservationId.value)
            )
        }
    }

    fun onReservationDeleteClicked(reservationId: Int) {
        baseViewModelScope.launch {
            showLoading()
            deleteReservationUseCase(reservationId = reservationId)
                .onSuccess {
                    _toastMessage.emit("게시글이 삭제되었습니다")
                    baseViewModelScope.launch {
                        _navigationHandler.emit(ReservationDetailNavigationAction.NavigateToBack)
                    }
                }
                .onError { }
            dismissLoading()
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
            _navigationHandler.emit(ReservationDetailNavigationAction.NavigateToUserProfile(userId = userId))

        }
    }
}