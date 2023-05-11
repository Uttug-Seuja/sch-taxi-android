package com.sch.sch_taxi.ui.setprofile



sealed class SetProfileNavigationAction {
//    class NavigateToSetProfileImage(val profile: Profile): SetProfileNavigationAction()
    object NavigateToSetProfileImage: SetProfileNavigationAction()
    object NavigateToHome: SetProfileNavigationAction()
    class NavigateToToastMessage(val message: String): SetProfileNavigationAction()
    object NavigateToAgeNumberPicker: SetProfileNavigationAction()

}