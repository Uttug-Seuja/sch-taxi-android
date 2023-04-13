package com.sch.sch_taxi.ui.taxicreate

sealed class TaxiCreateNavigationAction {
    object NavigateToTaxiDetail: TaxiCreateNavigationAction()
    object NavigateToBack: TaxiCreateNavigationAction()
    object NavigateToSelectGender: TaxiCreateNavigationAction()
    object NavigateToSelectReservation: TaxiCreateNavigationAction()
    object NavigateToSelectSeat: TaxiCreateNavigationAction()

}