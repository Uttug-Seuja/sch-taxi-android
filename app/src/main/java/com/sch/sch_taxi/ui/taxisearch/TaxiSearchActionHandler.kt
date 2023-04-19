package com.sch.sch_taxi.ui.taxisearch


interface TaxiSearchActionHandler {
    fun onClickedBack()
    fun onClickedTaxiSearchResult(searchTitle : String)
    fun onClickedDeleteSearchHistory(searchHistoryIdx: Int)
    fun onClickedDeleteSearchHistoryList()
    fun onClickedDeleteSearchTitle()


}