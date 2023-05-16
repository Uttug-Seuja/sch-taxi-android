package com.sch.sch_taxi.ui.myreservation


sealed class MyReservationNavigationAction {
    object NavigateToBack: MyReservationNavigationAction()
    object NavigateToTaxiRoom: MyReservationNavigationAction()
    object NavigateToChatting: MyReservationNavigationAction()


}