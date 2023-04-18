package com.sch.sch_taxi.ui.taxicreate

import com.sch.domain.model.KakaoLocal


interface TaxiCreateActionHandler {
    fun onClickedBack()
    fun onClickedTaxiCreate()
    fun onClickedSelectGander()
    fun onClickedSelectReservation()
    fun onClickedSelectSeat()
    fun onClickedKeyword(kakaoLocal: KakaoLocal)


}