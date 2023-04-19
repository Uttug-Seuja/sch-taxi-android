package com.sch.sch_taxi.ui.taxisearch

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sch.domain.model.SearchHistory
import com.sch.domain.model.SearchHistoryList
import com.sch.domain.model.Taxis
import com.sch.sch_taxi.ui.taxisearch.adapter.TaxiSearchAdapter
import com.sch.sch_taxi.ui.taxisearch.adapter.TaxiSearchHistoryAdapter

@BindingAdapter("constraintLayoutVisible")
fun ConstraintLayout.bindConstraintLayoutVisible(searchText : String) {
    if (searchText.isEmpty()) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}

@BindingAdapter("recyclerViewVisible")
fun RecyclerView.bindRecyclerViewVisible(searchText : String) {
    if (searchText.isEmpty()) this.visibility = View.GONE
    else this.visibility = View.VISIBLE
}

@BindingAdapter("imageViewVisible")
fun ImageView.bindImageViewVisible(searchText : String) {
    if (searchText.isEmpty()) this.visibility = View.GONE
    else this.visibility = View.VISIBLE
}

@BindingAdapter("taxiSearchHistoryAdapter")
fun RecyclerView.bindTaxiSearchHistoryAdapter(itemList: SearchHistoryList) {
    val boundAdapter = this.adapter
    if (boundAdapter is TaxiSearchHistoryAdapter) {
        boundAdapter.submitList(itemList.searchHistory)
    }
}

@BindingAdapter("taxiSearchAdapter")
fun RecyclerView.bindTaxiSearchAdapter(itemList: Taxis) {
    val boundAdapter = this.adapter
    if (boundAdapter is TaxiSearchAdapter) {
        boundAdapter.submitList(itemList.Taxi)
    }
}