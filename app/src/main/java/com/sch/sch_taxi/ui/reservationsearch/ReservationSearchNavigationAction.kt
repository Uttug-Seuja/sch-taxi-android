package com.sch.sch_taxi.ui.reservationsearch



sealed class ReservationSearchNavigationAction {
    class NavigateToTaxiSearchResult(val searchTitle: String): ReservationSearchNavigationAction()
    object NavigateToBack: ReservationSearchNavigationAction()

}