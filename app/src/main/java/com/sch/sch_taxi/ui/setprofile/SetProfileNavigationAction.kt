package com.sch.sch_taxi.ui.setprofile



sealed class SetProfileNavigationAction {
    object NavigateToHome: SetProfileNavigationAction()
    class NavigateToToastMessage(val message: String): SetProfileNavigationAction()
}