package com.sch.sch_taxi.ui.reservationsearch


interface ReservationSearchActionHandler {
    fun onClickedBack()
    fun onClickedTaxiSearchResult(searchTitle : String)
    fun onClickedDeleteSearchHistory(searchHistoryIdx: Int)
    fun onClickedDeleteSearchHistoryList()
    fun onClickedDeleteSearchTitle()


}