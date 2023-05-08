package com.sch.sch_taxi.ui.reservationdetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.sch.sch_taxi.R
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem

// 커스텀 말풍선 클래스
class CustomBalloonAdapter(inflater: LayoutInflater): CalloutBalloonAdapter {
    val mCalloutBalloon: View = inflater.inflate(R.layout.holder_balloon, null)
    val name: TextView = mCalloutBalloon.findViewById(R.id.ball_tv_name)
    val address: TextView = mCalloutBalloon.findViewById(R.id.ball_tv_address)

    override fun getCalloutBalloon(poiItem: MapPOIItem?): View {
        // 마커 클릭 시 나오는 말풍선
        name.text = poiItem?.itemName
        address.text = "오늘의 순천향 택시"
        return mCalloutBalloon
    }

    override fun getPressedCalloutBalloon(poiItem: MapPOIItem?): View {

        // 말풍선 클릭 시
        address.text = "오순택!"
        return mCalloutBalloon
    }
}
