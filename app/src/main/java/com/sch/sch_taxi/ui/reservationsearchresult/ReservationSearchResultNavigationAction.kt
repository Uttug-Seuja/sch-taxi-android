package com.sch.sch_taxi.ui.reservationsearchresult

sealed class ReservationSearchResultNavigationAction {
    object NavigateToBack: ReservationSearchResultNavigationAction()
    class NavigateToTaxiSearchResult(val searchTitle: String): ReservationSearchResultNavigationAction()
    class NavigateToTaxiDetail(val reservationId: Int): ReservationSearchResultNavigationAction()

}