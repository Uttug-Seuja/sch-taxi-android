package com.sch.sch_taxi.ui.reservationupdate


sealed class ReservationUpdateNavigationAction {
    object NavigateToBack: ReservationUpdateNavigationAction()
    object NavigateToSelectReservation: ReservationUpdateNavigationAction()
    object NavigateToKeywordClicked: ReservationUpdateNavigationAction()
    class NavigateToTaxiDetail(val id: Int): ReservationUpdateNavigationAction()

}