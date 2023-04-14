package com.sch.sch_taxi.ui.editprofile

sealed class EditProfileNavigationAction {
    object NavigateToLogout: EditProfileNavigationAction()
    object NavigateToUserDelete: EditProfileNavigationAction()
    object NavigateToEditProfile: EditProfileNavigationAction()
    object NavigateToSplash: EditProfileNavigationAction()

}