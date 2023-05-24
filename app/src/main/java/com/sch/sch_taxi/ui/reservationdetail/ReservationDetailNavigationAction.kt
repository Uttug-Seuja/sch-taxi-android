package com.sch.sch_taxi.ui.reservationdetail

sealed class ReservationDetailNavigationAction {
    object NavigateToBack : ReservationDetailNavigationAction()
    class NavigateToReservationMoreBottomDialog(val reservationId: Int, val sendUserId: Int) :
        ReservationDetailNavigationAction()
    class NavigateToReservationUpdate(val reservationId: Int) :
        ReservationDetailNavigationAction()

    object NavigateToSelectSeatBottomDialog : ReservationDetailNavigationAction()
    class NavigateToUserProfile(val userId: Int) : ReservationDetailNavigationAction()

}