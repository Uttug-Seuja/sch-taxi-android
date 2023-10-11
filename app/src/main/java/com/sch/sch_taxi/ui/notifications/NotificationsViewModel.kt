package com.sch.sch_taxi.ui.notifications

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sch.domain.model.Notification
import com.sch.domain.usecase.main.GetNotificationUseCase
import com.sch.sch_taxi.base.BaseViewModel
import com.sch.sch_taxi.ui.notifications.adapter.createNotificationPager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val getNotificationUseCase: GetNotificationUseCase
) : BaseViewModel(), NotificationsActionHandler {

    private val TAG = "NotificationsActionHandler"

    private val _navigationHandler: MutableSharedFlow<NotificationsNavigationAction> =
        MutableSharedFlow<NotificationsNavigationAction>()
    val navigationHandler: SharedFlow<NotificationsNavigationAction> =
        _navigationHandler.asSharedFlow()

    var notificationsEvent: Flow<PagingData<Notification>> = emptyFlow()


    init {
        getNotification()
    }

    fun getNotification() {
        notificationsEvent =
            createNotificationPager(getNotificationUseCase = getNotificationUseCase).flow.cachedIn(
                baseViewModelScope
            )
    }

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