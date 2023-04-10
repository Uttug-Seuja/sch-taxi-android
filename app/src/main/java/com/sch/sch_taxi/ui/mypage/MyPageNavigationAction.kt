package com.sch.sch_taxi.ui.mypage

sealed class MyPageNavigationAction {
    object NavigateToEditProfile: MyPageNavigationAction()
    object NavigateToMyFavorite: MyPageNavigationAction()

    object NavigateToAlarmSetting: MyPageNavigationAction()

}