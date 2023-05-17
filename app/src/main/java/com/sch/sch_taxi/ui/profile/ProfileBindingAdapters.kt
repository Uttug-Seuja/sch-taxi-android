package com.sch.sch_taxi.ui.profile

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter

@BindingAdapter("addImageCardViewVisible")
fun CardView.bindAddImageCardViewVisible(uri: String) {
    if (uri != "") this.visibility = View.GONE
    else this.visibility = View.VISIBLE
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

//@BindingAdapter("bookCoverStackAdapter")
//fun RecyclerView.bindBookCoverStackAdapter(itemList: BookCoverStacks) {
//    val boundAdapter = this.adapter
//    if (boundAdapter is BookCoverStack2Adapter) {
//        boundAdapter.submitList(itemList.bookCoverStacks)
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

@BindingAdapter("mannerTemperatureInfoVisible")
fun ConstraintLayout.bindMannerTemperatureInfoVisible(clickable: Boolean) {
    if (clickable) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}

@BindingAdapter("progressPercent")
fun ProgressBar.bindProgressPercent(progress: Double){
    Log.d("ttt progress",progress.toString() )
    this.max = 100
    this.progress = progress.toInt()
}