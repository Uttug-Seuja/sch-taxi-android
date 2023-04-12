package com.sch.sch_taxi.ui.taxisearch

import com.sch.sch_taxi.ui.home.HomeNavigationAction

sealed class TaxiSearchNavigationAction {
    object NavigateToTaxiSearchResult: TaxiSearchNavigationAction()

}