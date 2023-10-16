package com.sch.sch_taxi.ui.myparticipation

import com.sch.domain.model.Reservation
import com.sch.domain.onError
import com.sch.domain.onSuccess
import com.sch.domain.usecase.main.GetUserParticipationUseCase
import com.sch.sch_taxi.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyParticipationViewModel @Inject constructor(
    private val getUserParticipationUseCase: GetUserParticipationUseCase
) : BaseViewModel(), MyParticipationActionHandler {

    private val TAG = "MyParticipationViewModel"

    private val _navigationHandler: MutableSharedFlow<MyParticipationNavigationAction> =
        MutableSharedFlow<MyParticipationNavigationAction>()
    val navigationHandler: SharedFlow<MyParticipationNavigationAction> =
        _navigationHandler.asSharedFlow()

    private val _userParticipationEvent: MutableStateFlow<List<Reservation>> =
        MutableStateFlow(listOf())
    val userParticipationEvent: StateFlow<List<Reservation>> = _userParticipationEvent

    init {
        getUserParticipation()
    }

    fun getUserParticipation() {
        baseViewModelScope.launch {
            getUserParticipationUseCase()
                .onSuccess {
                    _userParticipationEvent.value = it
                }
                .onError {
                }
        }
    }
    override fun onClickedBack() {
        baseViewModelScope.launch {
            _navigationHandler.emit(MyParticipationNavigationAction.NavigateToBack)
        }
    }

    override fun onClickedNotification() {
        baseViewModelScope.launch {
            _navigationHandler.emit(MyParticipationNavigationAction.NavigateToBack)
        }
    }
    override fun onClickedTaxiDetail(reservationId: Int) {
        baseViewModelScope.launch {
            _navigationHandler.emit(MyParticipationNavigationAction.NavigateToTaxiDetail(reservationId = reservationId))
        }
    }
}