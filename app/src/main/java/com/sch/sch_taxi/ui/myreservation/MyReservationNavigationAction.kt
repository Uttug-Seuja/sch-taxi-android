package com.sch.sch_taxi.ui.myreservation


sealed class MyReservationNavigationAction {
    object NavigateToBack: MyReservationNavigationAction()
    class NavigateToTaxiDetail(val reservationId: Int): MyReservationNavigationAction()


}