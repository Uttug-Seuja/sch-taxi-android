package com.sch.sch_taxi.ui.myparticipation

import com.sch.domain.model.Taxis
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

    private val _notificationsEvent: MutableStateFlow<Taxis> =
        MutableStateFlow(Taxis(emptyList()))
    val notificationsEvent: StateFlow<Taxis> = _notificationsEvent

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
}