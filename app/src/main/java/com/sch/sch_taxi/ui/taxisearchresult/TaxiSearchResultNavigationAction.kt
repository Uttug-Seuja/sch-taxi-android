package com.sch.sch_taxi.ui.taxisearchresult

sealed class TaxiSearchResultNavigationAction {
    object NavigateToBack: TaxiSearchResultNavigationAction()

    object NavigateToTaxiSearchResult: TaxiSearchResultNavigationAction()

}