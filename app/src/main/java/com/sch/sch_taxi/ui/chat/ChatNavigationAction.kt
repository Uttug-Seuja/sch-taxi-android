package com.sch.sch_taxi.ui.chat

sealed class ChatNavigationAction {
    object NavigateToBack: ChatNavigationAction()
    object NavigateToTaxiRoom: ChatNavigationAction()
    object NavigateToChatting: ChatNavigationAction()


}