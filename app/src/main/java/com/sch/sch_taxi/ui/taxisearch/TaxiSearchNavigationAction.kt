package com.sch.sch_taxi.ui.taxisearch



sealed class TaxiSearchNavigationAction {
    class NavigateToTaxiSearchResult(val searchTitle: String): TaxiSearchNavigationAction()
    object NavigateToBack: TaxiSearchNavigationAction()

}