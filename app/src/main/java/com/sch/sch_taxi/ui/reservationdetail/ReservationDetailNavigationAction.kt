package com.sch.sch_taxi.ui.reservationdetail

sealed class ReservationDetailNavigationAction {
    object NavigateToBack : ReservationDetailNavigationAction()
    class NavigateToReservationMoreBottomDialog(val reservationId: Int, val sendUserId: Int) :
        ReservationDetailNavigationAction()

}