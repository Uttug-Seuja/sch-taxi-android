package com.sch.sch_taxi.ui.reservationupdate

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sch.domain.model.KakaoLocals
import com.sch.sch_taxi.ui.reservationupdate.adapter.KakaoLocalUpdateAdapter
import kotlinx.coroutines.flow.MutableStateFlow


@BindingAdapter("kakaoLocalUpdateAdapter")
fun RecyclerView.bindKakaoLocalUpdateAdapter(itemList: KakaoLocals) {
    val boundAdapter = this.adapter
    if (boundAdapter is KakaoLocalUpdateAdapter) {
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