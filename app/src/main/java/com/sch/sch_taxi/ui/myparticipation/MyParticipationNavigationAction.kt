package com.sch.sch_taxi.ui.myparticipation


sealed class MyParticipationNavigationAction {
    object NavigateToBack: MyParticipationNavigationAction()
    object NavigateToTaxiRoom: MyParticipationNavigationAction()
    object NavigateToChatting: MyParticipationNavigationAction()


}