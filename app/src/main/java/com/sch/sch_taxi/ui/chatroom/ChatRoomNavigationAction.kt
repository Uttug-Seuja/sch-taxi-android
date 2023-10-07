package com.sch.sch_taxi.ui.chatroom

sealed class ChatRoomNavigationAction {
    object NavigateToBack : ChatRoomNavigationAction()
    class NavigateToReservationMoreBottomDialog(val reservationId: Int, val sendUserId: Int) :
        ChatRoomNavigationAction()
    class NavigateToReservationUpdate(val reservationId: Int) : ChatRoomNavigationAction()
    object NavigateToSelectSeatBottomDialog : ChatRoomNavigationAction()
    class NavigateToUserProfile(val userId: Int) : ChatRoomNavigationAction()
}