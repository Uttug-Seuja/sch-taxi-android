package com.sch.sch_taxi.ui.notifications

import com.sch.sch_taxi.ui.taxisearch.TaxiSearchNavigationAction

sealed class NotificationsNavigationAction {
    object NavigateToBack: NotificationsNavigationAction()
    object NavigateToTaxiRoom: NotificationsNavigationAction()
    object NavigateToChatting: NotificationsNavigationAction()


}