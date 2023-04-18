package com.sch.sch_taxi.ui.notifications


sealed class NotificationsNavigationAction {
    object NavigateToBack: NotificationsNavigationAction()
    object NavigateToTaxiRoom: NotificationsNavigationAction()
    object NavigateToChatting: NotificationsNavigationAction()
}