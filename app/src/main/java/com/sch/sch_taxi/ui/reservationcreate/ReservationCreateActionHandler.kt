package com.sch.sch_taxi.ui.reservationcreate

import com.sch.domain.model.KakaoLocal


interface ReservationCreateActionHandler {
    fun onClickedBack()
    fun onClickedTaxiCreate()
    fun onClickedSelectGander()
    fun onClickedSelectReservation()
    fun onClickedSelectSeat()
    fun onClickedKeyword(kakaoLocal: KakaoLocal)


}