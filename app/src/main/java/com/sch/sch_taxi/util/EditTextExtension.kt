package com.sch.sch_taxi.util

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.EditText
import com.sch.sch_taxi.R

@SuppressLint("UseCompatLoadingForDrawables")
fun EditText.customOnFocusChangeListener(context: Context) {
    this.onFocusChangeListener = View.OnFocusChangeListener { view, gainFocus ->
        //포커스가 주어졌을 때
        if (gainFocus) view.background = context.getDrawable(R.drawable.custom_backgroundwhite_radius16_line_gray06)
        else view.background = context.getDrawable(R.drawable.custom_backgroundwhite_radius16)
    }
}
