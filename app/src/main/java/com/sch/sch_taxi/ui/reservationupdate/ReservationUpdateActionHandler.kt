package com.sch.sch_taxi.ui.reservationupdate

import com.sch.domain.model.KakaoLocal


interface ReservationUpdateActionHandler {
    fun onClickedBack()
    fun onClickedTaxiUpdate()
    fun onClickedSelectReservation()
    fun onClickedKeyword(kakaoLocal: KakaoLocal)
}