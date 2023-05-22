package com.sch.sch_taxi.ui.home

import android.graphics.Color
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView
import com.sch.domain.model.Reservation


private val gender =
    hashMapOf<String, String>("ALL" to "남녀모두", "MAN" to "남자만", "WOMAN" to "여자만")
private val reserveStatus =
    hashMapOf<String, String>("POSSIBLE" to "신청가능", "IMMINENT" to "마감임박!", "DEADLINE" to "마감")
private val stateTextColor = hashMapOf<String, String>(
    "POSSIBLE" to "#FFFFFF",
    "IMMINENT" to "#FFFFFF",
    "DEADLINE" to "#cccccc"
)
private val stateBtnColor = hashMapOf<String, String>(
    "POSSIBLE" to "#1570ff",
    "IMMINENT" to "#FF4D37",
    "DEADLINE" to "#EEEEEE"
)

@BindingAdapter("reserveInfoBtnDescription")
fun TextView.bindReserveInfoBtnDescription(reservationStatus: String?) {
    reservationStatus?.let {
        this.text = reserveStatus[it]
        this.setTextColor(Color.parseColor(stateTextColor[it]))
    }
}

@BindingAdapter("reserveInfoBtn")
fun MaterialCardView.bindReserveInfoBtn(reservationStatus: String?) {
    reservationStatus?.let {
        this.setCardBackgroundColor(Color.parseColor(stateBtnColor[it]))
    }
}

@BindingAdapter("reserveInfoText")
fun TextView.bindReserveInfoText(reservation: Reservation) {
    this.text = "%s->%s•%s".format(
        reservation.startPoint,
        reservation.destination,
        gender[reservation.gender]
    )
}