package com.sch.sch_taxi.ui.chatroom


interface ChatRoomActionHandler {
    fun onClickedBack()
    fun onClickedNotification()
    fun onClickedSelectSeatBottomDialog()
    fun onClickedUserProfile(userId: Int)

}