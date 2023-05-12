package com.sch.sch_taxi.ui.mypost

import com.sch.domain.model.Taxis
import com.sch.domain.usecase.main.GetUserReservationUseCase
import com.sch.sch_taxi.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPostViewModel @Inject constructor(
    private val getUserReservationUseCase: GetUserReservationUseCase
) : BaseViewModel(), MyPostActionHandler {

    private val TAG = "MyPostViewModel"

    private val _navigationHandler: MutableSharedFlow<MyPostNavigationAction> =
        MutableSharedFlow<MyPostNavigationAction>()
    val navigationHandler: SharedFlow<MyPostNavigationAction> =
        _navigationHandler.asSharedFlow()

    private val _notificationsEvent: MutableStateFlow<Taxis> =
        MutableStateFlow(Taxis(emptyList()))
    val notificationsEvent: StateFlow<Taxis> = _notificationsEvent

    override fun onClickedBack() {
        baseViewModelScope.launch {
            _navigationHandler.emit(MyPostNavigationAction.NavigateToBack)
        }
    }

    override fun onClickedNotification() {
        baseViewModelScope.launch {
            _navigationHandler.emit(MyPostNavigationAction.NavigateToBack)
        }
    }
}