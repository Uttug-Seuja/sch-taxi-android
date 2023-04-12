package com.sch.sch_taxi.ui.notifications

import com.sch.domain.model.Taxis
import com.sch.sch_taxi.base.BaseViewModel
import com.sch.sch_taxi.ui.taxisearch.TaxiSearchNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
) : BaseViewModel(), NotificationsActionHandler {

    private val TAG = "NotificationsActionHandler"

    private val _navigationHandler: MutableSharedFlow<NotificationsNavigationAction> =
        MutableSharedFlow<NotificationsNavigationAction>()
    val navigationHandler: SharedFlow<NotificationsNavigationAction> =
        _navigationHandler.asSharedFlow()

    private val _notificationsEvent: MutableStateFlow<Taxis> =
        MutableStateFlow(Taxis(emptyList()))
    val notificationsEvent: StateFlow<Taxis> = _notificationsEvent

    override fun onClickedBack() {
        baseViewModelScope.launch {
            _navigationHandler.emit(NotificationsNavigationAction.NavigateToBack)
        }
    }

    override fun onClickedNotification() {
        baseViewModelScope.launch {
            _navigationHandler.emit(NotificationsNavigationAction.NavigateToBack)
        }
    }
}