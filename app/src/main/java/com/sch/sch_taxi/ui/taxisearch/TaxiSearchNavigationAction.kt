package com.sch.sch_taxi.ui.taxisearch



sealed class TaxiSearchNavigationAction {
    object NavigateToTaxiSearchResult: TaxiSearchNavigationAction()
    object NavigateToBack: TaxiSearchNavigationAction()

}