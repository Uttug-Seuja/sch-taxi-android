package com.sch.sch_taxi.ui.chatroom

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sch.domain.model.Taxis
import com.sch.sch_taxi.ui.myparticipation.adapter.MyParticipationAdapter

@BindingAdapter("addImageCardViewVisible")
fun CardView.bindAddImageCardViewVisible(uri: String) {
    if (uri != "") this.visibility = View.GONE
    else this.visibility = View.VISIBLE
}

@BindingAdapter("imgLoadVisible")
fun CardView.bindImgLoadVisible(uri: String) {
    if (uri != "") this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}

@BindingAdapter("deleteTextVisible")
fun TextView.bindDeleteTextVisible(text: Int) {
    if (text == 0) {
        this.setTextColor(Color.parseColor("#BDBDBD"))
        this.isClickable = false
    } else {
        this.setTextColor(Color.parseColor("#212121"))
        this.isClickable = true
    }
}

@BindingAdapter("editTextVisible")
fun TextView.bindEditTextVisible(textLength: Int) {
    if (textLength == 0) {
        this.setTextColor(Color.parseColor("#BDBDBD"))
        this.isClickable = false
    } else {
        this.setTextColor(Color.parseColor("#FFD260"))
        this.isClickable = true
    }
}

//@BindingAdapter("notificationsAdapter")
//fun RecyclerView.bindNotificationsAdapter(itemList: Taxis) {
//    val boundAdapter = this.adapter
//    if (boundAdapter is MyParticipationAdapter) {
//        boundAdapter.submitList(itemList.Taxi)
//    }
//}

@BindingAdapter("textVisible")
fun TextView.bindTextVisible(text: String) {
    if (text != "") this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}

@BindingAdapter("editTextCountColorChange")
fun TextView.bindEditTextCountColorChange(textLength: Int) {
    this.text = "$textLength/200"
    if (textLength == 0) this.text = this.textChangeColor( "#ff0000", 0, 1)
    else this.text = this.textChangeColor( "#616161", 0, textLength.toString().length)
}

fun TextView.textChangeColor(
    color: String,
    start: Int,
    end: Int
): SpannableStringBuilder {
    val builder = SpannableStringBuilder(this.text.toString())

    builder.setSpan(
        ForegroundColorSpan(Color.parseColor(color)),
        start,
        end,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    return builder
}

@SuppressLint("UseCompatLoadingForDrawables")
fun EditText.titleTextOnFocusChangeListener(imageView: ImageView) {
    this.onFocusChangeListener = View.OnFocusChangeListener { view, gainFocus ->
        //포커스가 주어졌을 때
        if (gainFocus) {
            this.setTextColor(Color.parseColor("#757575"))
            imageView.visibility = View.GONE
        }
        else {
            this.setTextColor(Color.parseColor("#212121"))
            imageView.visibility = View.VISIBLE
        }
    }
}