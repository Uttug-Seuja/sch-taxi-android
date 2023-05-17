package com.sch.sch_taxi.ui.myparticipation


sealed class MyParticipationNavigationAction {
    object NavigateToBack: MyParticipationNavigationAction()
    class NavigateToTaxiDetail(val reservationId: Int): MyParticipationNavigationAction()


}