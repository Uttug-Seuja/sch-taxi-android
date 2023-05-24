package com.sch.sch_taxi.ui.reservationcreate

sealed class ReservationCreateNavigationAction {
    class NavigateToTaxiDetail(val id: Int): ReservationCreateNavigationAction()
    object NavigateToBack: ReservationCreateNavigationAction()
    object NavigateToSelectGender: ReservationCreateNavigationAction()
    object NavigateToSelectReservation: ReservationCreateNavigationAction()
    object NavigateToSelectSeat: ReservationCreateNavigationAction()
    object NavigateToKeywordClicked: ReservationCreateNavigationAction()

}