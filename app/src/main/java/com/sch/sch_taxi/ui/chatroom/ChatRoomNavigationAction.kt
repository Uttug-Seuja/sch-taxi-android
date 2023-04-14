package com.sch.sch_taxi.ui.chatroom

sealed class ChatRoomNavigationAction {
    object NavigateToBack: ChatRoomNavigationAction()
    object NavigateToTaxiRoom: ChatRoomNavigationAction()
    object NavigateToChatting: ChatRoomNavigationAction()


}