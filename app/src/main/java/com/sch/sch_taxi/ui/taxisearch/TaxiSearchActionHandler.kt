package com.sch.sch_taxi.ui.taxisearch


interface TaxiSearchActionHandler {
    fun onClickedBack()
    fun onClickedTaxiSearchResult()
    fun onClickedDeleteSearchHistory(searchHistoryIdx: Int)
    fun onClickedDeleteSearchHistoryList()

}