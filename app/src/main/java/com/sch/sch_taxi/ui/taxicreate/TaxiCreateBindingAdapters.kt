package com.sch.sch_taxi.ui.taxicreate

import android.annotation.SuppressLint
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
import androidx.recyclerview.widget.RecyclerView
import com.sch.domain.model.KakaoLocal
import com.sch.domain.model.KakaoLocals
import com.sch.sch_taxi.ui.taxicreate.adapter.KakaoLocalAdapter
import kotlinx.coroutines.flow.MutableStateFlow


@BindingAdapter("kakaoLocalAdapter")
fun RecyclerView.bindKakaoLocalAdapter(itemList: KakaoLocals) {
    val boundAdapter = this.adapter
    if (boundAdapter is KakaoLocalAdapter) {
        boundAdapter.submitList(itemList.kakaoLocal)
    }
}

@SuppressLint("UseCompatLoadingForDrawables")
fun EditText.searchEditTextOnFocusChangeListener(
    recyclerView: RecyclerView,
    linearLayout: LinearLayout,
    isKeyword: MutableStateFlow<Boolean>
) {
    this.onFocusChangeListener = View.OnFocusChangeListener { view, gainFocus ->
        //포커스가 주어졌을 때
        if (gainFocus) {
            this.setTextColor(Color.parseColor("#757575"))
            recyclerView.visibility = View.VISIBLE
            linearLayout.visibility = View.GONE
            isKeyword.value = true
        } else {
            this.setTextColor(Color.parseColor("#212121"))
            recyclerView.visibility = View.GONE
            linearLayout.visibility = View.VISIBLE
            isKeyword.value = false
        }
    }
}