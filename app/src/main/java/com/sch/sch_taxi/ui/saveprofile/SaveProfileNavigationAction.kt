package com.sch.sch_taxi.ui.saveprofile

sealed class SaveProfileNavigationAction {
    object NavigateToSuccess: SaveProfileNavigationAction()
    object NavigateToEditProfileImg: SaveProfileNavigationAction()
}