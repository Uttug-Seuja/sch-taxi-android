package com.sch.sch_taxi.ui.myreservation

import com.sch.domain.model.Taxis
import com.sch.domain.usecase.main.GetUserReservationUseCase
import com.sch.sch_taxi.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyReservationViewModel @Inject constructor(
    private val getUserReservationUseCase: GetUserReservationUseCase
) : BaseViewModel(), MyReservationActionHandler {

    private val TAG = "MyReservationViewModel"

    private val _navigationHandler: MutableSharedFlow<MyReservationNavigationAction> =
        MutableSharedFlow<MyReservationNavigationAction>()
    val navigationHandler: SharedFlow<MyReservationNavigationAction> =
        _navigationHandler.asSharedFlow()

    private val _notificationsEvent: MutableStateFlow<Taxis> =
        MutableStateFlow(Taxis(emptyList()))
    val notificationsEvent: StateFlow<Taxis> = _notificationsEvent

    override fun onClickedBack() {
        baseViewModelScope.launch {
            _navigationHandler.emit(MyReservationNavigationAction.NavigateToBack)
        }
    }

    override fun onClickedNotification() {
        baseViewModelScope.launch {
            _navigationHandler.emit(MyReservationNavigationAction.NavigateToBack)
        }
    }
}