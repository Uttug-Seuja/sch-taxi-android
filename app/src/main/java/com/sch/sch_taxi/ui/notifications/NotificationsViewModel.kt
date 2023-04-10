package com.sch.sch_taxi.ui.notifications

import com.sch.sch_taxi.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
) : BaseViewModel(), NotificationsActionHandler {

    private val TAG = "NotificationsActionHandler"

}