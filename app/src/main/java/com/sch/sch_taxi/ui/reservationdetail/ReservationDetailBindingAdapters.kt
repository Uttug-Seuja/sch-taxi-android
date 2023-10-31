package com.sch.sch_taxi.ui.reservationdetail

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView
import com.sch.domain.model.ReservationDetail
import com.sch.sch_taxi.R
import org.w3c.dom.Text

private val sex =
    hashMapOf<String, String>("MAN" to "남자", "WOMAN" to "여자")

private val gender =
    hashMapOf<String, String>("ALL" to "남녀모두", "MAN" to "남자만", "WOMAN" to "여자만")
private val reserveStatus =
    hashMapOf<String, String>("CHATTING" to "채팅하기", "POSSIBLE" to "좌석선택", "IMMINENT" to "좌석선택", "DEADLINE" to "신청 마감")
private val stateTextColor = hashMapOf<String, String>(
    "CHATTING" to "#FFFFFF",
    "POSSIBLE" to "#FFFFFF",
    "IMMINENT" to "#FFFFFF",
    "DEADLINE" to "#cccccc"
)
private val stateBtnColor = hashMapOf<String, String>(
    "CHATTING" to "#1570ff",
    "POSSIBLE" to "#1570ff",
    "IMMINENT" to "#1570ff",
    "DEADLINE" to "#EEEEEE"
)

private val reserveStatusText =
    hashMapOf<String, String>("CHATTING" to "신청한 사람들과\n약속을 잡아봐요!", "POSSIBLE" to "지금 신청하면\n진행확정이 빨라져요!", "IMMINENT" to "곧 마감됩니다!\n지금 신청하세요", "DEADLINE" to "다른 예약을 신청하세요")


@BindingAdapter("sexText")
fun TextView.bindSexText(sexEnum : String?) {
    sexEnum?.let {
        this.text = sex[it]
    }
}

@BindingAdapter("genderText")
fun TextView.bindGenderText(genderEnum : String?){
    genderEnum?.let{
        this.text = gender[it]
    }
}

@BindingAdapter("applyText")
fun TextView.bindApplyText(reservationStatus: String?){
    reservationStatus?.let {
        this.text = reserveStatus[it]
        this.setTextColor(Color.parseColor(stateTextColor[it]))
    }
}

@BindingAdapter("applyBtn")
fun MaterialCardView.bindApplyBtn(reservationStatus: String?){
    reservationStatus?.let {
        this.isEnabled = it != "DEADLINE"
        this.setCardBackgroundColor(Color.parseColor(stateBtnColor[it]))
    }
}

@BindingAdapter("reserveStatusText")
fun TextView.bindReserveStatusText(reservationStatus: String?){
    reservationStatus?.let {
        this.text = reserveStatusText[it]
    }
}

@SuppressLint("UseCompatLoadingForDrawables")
fun EditText.messageTextOnFocusChangeListener(context: Context, linearLayout: LinearLayout) {
    this.onFocusChangeListener = View.OnFocusChangeListener { view, gainFocus ->
        //포커스가 주어졌을 때
        if (gainFocus) linearLayout.background = context.getDrawable(R.drawable.custom_backgroundgray03_radius10_line_gray08)
        else linearLayout.background = context.getDrawable(R.drawable.custom_backgroundgray03_radius10)
    }
}

@BindingAdapter("initText")
fun TextView.bindInitText(reservationDetail : ReservationDetail?){
    if (reservationDetail == null) this.text = ""
    else this.text = reservationDetail.startPoint + "->" + reservationDetail.destination

}