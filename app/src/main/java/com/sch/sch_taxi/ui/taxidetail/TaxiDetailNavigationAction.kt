package com.sch.sch_taxi.ui.taxidetail

sealed class TaxiDetailNavigationAction {
    object NavigateToBack : TaxiDetailNavigationAction()
    class NavigateToTaxiMoreBottomDialog(val taxiId: Int, val sendUserId: Int) :
        TaxiDetailNavigationAction()

}