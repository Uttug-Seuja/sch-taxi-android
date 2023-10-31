package com.sch.sch_taxi.ui.profile

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView

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

@BindingAdapter("moreBtnEnabled")
fun ImageView.bindMoreBtnEnabled(userId: Int) {
    this.isEnabled = userId != -1
}