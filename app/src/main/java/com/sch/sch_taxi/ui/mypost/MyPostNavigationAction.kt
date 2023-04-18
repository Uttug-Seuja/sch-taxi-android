package com.sch.sch_taxi.ui.mypost


sealed class MyPostNavigationAction {
    object NavigateToBack: MyPostNavigationAction()
    object NavigateToTaxiRoom: MyPostNavigationAction()
    object NavigateToChatting: MyPostNavigationAction()


}