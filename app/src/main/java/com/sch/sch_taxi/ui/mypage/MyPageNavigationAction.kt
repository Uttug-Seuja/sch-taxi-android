package com.sch.sch_taxi.ui.mypage

sealed class MyPageNavigationAction {
    object NavigateToProfile: MyPageNavigationAction()
    object NavigateToMyReservation: MyPageNavigationAction()
    object NavigateToMyParticipation: MyPageNavigationAction()
    object NavigateToAlarmSetting: MyPageNavigationAction()
    object NavigateToRegister: MyPageNavigationAction()
    object NavigateToLogoutDialog: MyPageNavigationAction()


}