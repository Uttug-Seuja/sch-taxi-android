package com.sch.sch_taxi.ui.setprofile

import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView
import org.w3c.dom.Text
import java.util.Timer
import java.util.TimerTask

fun textChangeColor(
    text: TextView,
    color: String,
    start: Int,
    end: Int
): SpannableStringBuilder {
    val builder = SpannableStringBuilder(text.text.toString())

    builder.setSpan(
        ForegroundColorSpan(Color.parseColor(color)),
        start,
        end,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    return builder
}

@BindingAdapter("authTimer")
fun TextView.bindAuthTimer(isAuth: Boolean) {

//    var mSecond: Long = 3000
//    val mTimer = Timer()
//    // 반복적으로 사용할 TimerTask
//    val mTimerTask = object : TimerTask() {
//        override fun run() {
//            val mHandler = Handler(Looper.getMainLooper())
//            mHandler.postDelayed({
//                // 반복실행할 구문
//                mSecond--
//                if (mSecond <= 0) {
//                    mTimer.cancel()
//                }
//                this@bindAuthTimer.text = "$mSecond 초"
//            }, 0)
//        }
//    }
//
//    if (isAuth) {
//        this.isEnabled = false
//        mTimerTask.run()
//    } else {
//        this.isEnabled = true
////        mTimerTask.cancel()
//    }

}

@BindingAdapter("textChangeColor")
fun TextView.bindTextChangeColor(textLength: Int) {
    if (textLength > 0) {
        this.isEnabled = true
        this.setTextColor(Color.parseColor("#000000"))
    } else {
        this.setTextColor(Color.parseColor("#BDBDBD"))
        this.isEnabled = false
    }
}
@BindingAdapter("editTextChangeEnabled")
fun EditText.bindEditTextChangeEnabled(isSuccess : Boolean) {
    this.isEnabled = !isSuccess
}

@BindingAdapter("textChangeEnabled")
fun TextView.bindTextChangeEnabled(isSuccess : Boolean) {
    this.isEnabled = !isSuccess

}

@BindingAdapter("nicknameEditTextCount")
fun TextView.bindNicknameEditTextCount(textLength: Int) {
    this.text = "${0}/15".format(textLength)
    if (textLength != 0) this.setTextColor(Color.parseColor("#000000"))
    else this.setTextColor(Color.parseColor("#BDBDBD"))
}

@BindingAdapter("manBtnClicked")
fun MaterialCardView.bindManBtnClicked(isMan: Boolean?) {
    if (isMan == true) this.strokeColor = Color.parseColor("#3F80FF")
    else this.strokeColor = Color.parseColor("#d9d9d9")
}

@BindingAdapter("womanBtnClicked")
fun MaterialCardView.bindWomanBtnClicked(isMan: Boolean?) {
    if (isMan == false) this.strokeColor = Color.parseColor("#3F80FF")
    else this.strokeColor = Color.parseColor("#d9d9d9")
}