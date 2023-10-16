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
import com.google.android.material.card.MaterialCardView

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

@BindingAdapter("cardViewVisible")
fun MaterialCardView.bindCardViewVisible(userId: Int) {
    if (userId == -1) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}

@BindingAdapter("mannerTemperatureInfoVisible")
fun ConstraintLayout.bindMannerTemperatureInfoVisible(clickable: Boolean) {
    if (clickable) this.visibility = View.VISIBLE
    else this.visibility = View.INVISIBLE
}

@BindingAdapter("mannerTemperatureInfoVisible")
fun View.bindMannerTemperatureInfoVisible(clickable: Boolean) {
    if (clickable) this.visibility = View.VISIBLE
    else this.visibility = View.INVISIBLE
}

@BindingAdapter("progressPercent")
fun ProgressBar.bindProgressPercent(progress: Double) {
    this.max = 100
    this.progress = progress.toInt()
}