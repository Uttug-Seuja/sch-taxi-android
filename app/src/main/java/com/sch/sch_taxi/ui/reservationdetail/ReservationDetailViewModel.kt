package com.sch.sch_taxi.ui.reservationdetail

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
import javax.inject.Inject

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

    var reservationId = MutableStateFlow<Int>(0)
    private val _notificationsEvent: MutableStateFlow<Taxis> =
        MutableStateFlow(Taxis(emptyList()))
    val notificationsEvent: StateFlow<Taxis> = _notificationsEvent
    var editTextReportEvent = MutableStateFlow<String>("")

    init {
        baseViewModelScope.launch {
            getReservationDetailUseCase(reservationId = reservationId.value)
                .onSuccess { }
                .onError { }

        }

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


}