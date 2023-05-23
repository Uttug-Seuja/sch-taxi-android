package com.sch.sch_taxi.ui.reservationsearchresult


interface ReservationSearchResultActionHandler {
    fun onClickedBack()
    fun onClickedTaxiSearchResult(searchTitle : String)
    fun onClickedDeleteSearchTitle()
    fun onClickedTaxiDetail(reservationId: Int)

}