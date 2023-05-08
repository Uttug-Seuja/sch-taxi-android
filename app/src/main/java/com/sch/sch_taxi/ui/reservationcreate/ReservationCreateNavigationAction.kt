package com.sch.sch_taxi.ui.reservationcreate

sealed class ReservationCreateNavigationAction {
    object NavigateToTaxiDetail: ReservationCreateNavigationAction()
    object NavigateToBack: ReservationCreateNavigationAction()
    object NavigateToSelectGender: ReservationCreateNavigationAction()
    object NavigateToSelectReservation: ReservationCreateNavigationAction()
    object NavigateToSelectSeat: ReservationCreateNavigationAction()
    object NavigateToKeywordClicked: ReservationCreateNavigationAction()

}