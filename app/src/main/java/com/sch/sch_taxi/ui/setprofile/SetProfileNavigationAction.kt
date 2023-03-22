package com.sch.sch_taxi.ui.setprofile



sealed class SetProfileNavigationAction {
//    class NavigateToSetProfileImage(val profile: Profile): SetProfileNavigationAction()
    object NavigateToSetProfileImage: SetProfileNavigationAction()
    object NavigateToHome: SetProfileNavigationAction()
    object NavigateToEmpty: SetProfileNavigationAction()
    object NavigateToAgeNumberPicker: SetProfileNavigationAction()

}