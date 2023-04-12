package com.sch.sch_taxi.ui.taxidetail

import com.sch.sch_taxi.ui.notifications.NotificationsNavigationAction

sealed class TaxiDetailNavigationAction {
    object NavigateToBack : TaxiDetailNavigationAction()
    class NavigateToTaxiMoreBottomDialog(val taxiId: Int, val sendUserId: Int) :
        TaxiDetailNavigationAction()

}