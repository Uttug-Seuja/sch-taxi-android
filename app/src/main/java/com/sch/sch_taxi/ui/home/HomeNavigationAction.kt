package com.sch.sch_taxi.ui.home

sealed class HomeNavigationAction {
    object NavigateToSearch: HomeNavigationAction()
    object NavigateToNotifications: HomeNavigationAction()
    object NavigateToTaxiCreate: HomeNavigationAction()
    object NavigateToTaxiDetail: HomeNavigationAction()
}