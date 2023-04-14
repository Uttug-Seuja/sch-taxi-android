package com.sch.sch_taxi.ui.editprofile.saveprofile

sealed class SaveProfileNavigationAction {
    object NavigateToSuccess: SaveProfileNavigationAction()
    object NavigateToEditProfileImg: SaveProfileNavigationAction()
}