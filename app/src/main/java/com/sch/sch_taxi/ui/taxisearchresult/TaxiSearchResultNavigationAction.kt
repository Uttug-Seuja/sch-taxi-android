package com.sch.sch_taxi.ui.taxisearchresult

import com.sch.sch_taxi.ui.taxisearch.TaxiSearchNavigationAction

sealed class TaxiSearchResultNavigationAction {
    object NavigateToBack: TaxiSearchResultNavigationAction()

    object NavigateToTaxiSearchResult: TaxiSearchResultNavigationAction()

}