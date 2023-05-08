package com.sch.sch_taxi.ui.reservationsearchresult

sealed class ReservationSearchResultNavigationAction {
    object NavigateToBack: ReservationSearchResultNavigationAction()
    object NavigateToTaxiSearchResult: ReservationSearchResultNavigationAction()

}