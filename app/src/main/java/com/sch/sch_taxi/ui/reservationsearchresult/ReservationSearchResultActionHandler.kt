package com.sch.sch_taxi.ui.reservationsearchresult


interface ReservationSearchResultActionHandler {
    fun onClickedBack()
    fun onClickedTaxiSearchResult()
    fun onClickedDeleteSearchTitle()
    fun onClickedTaxiDetail(reservationId: Int)

}