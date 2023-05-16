package com.sch.sch_taxi.ui.reservationdetail


interface ReservationDetailActionHandler {
    fun onClickedBack()
    fun onClickedReservationMoreBottomDialog()
    fun onClickedSelectSeatBottomDialog()
    fun onClickedUserProfile(userId: Int)

}