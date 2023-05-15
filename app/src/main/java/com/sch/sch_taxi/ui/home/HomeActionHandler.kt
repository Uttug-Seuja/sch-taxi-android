package com.sch.sch_taxi.ui.home


interface HomeActionHandler {
    fun onClickedSearch()
    fun onClickedTaxiDetail(reservationId: Int)
    fun onClickedNotifications()
    fun onClickedTaxiCreate()

}