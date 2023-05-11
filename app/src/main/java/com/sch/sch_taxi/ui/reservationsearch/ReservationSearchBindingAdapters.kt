package com.sch.sch_taxi.ui.reservationsearch

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sch.domain.model.RecommendKeywordList
import com.sch.domain.model.SearchHistoryList
import com.sch.domain.model.Taxis
import com.sch.sch_taxi.ui.reservationsearch.adapter.RecommendKeywordAdapter
import com.sch.sch_taxi.ui.reservationsearch.adapter.ReservationSearchHistoryAdapter

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
    if (boundAdapter is ReservationSearchHistoryAdapter) {
        boundAdapter.submitList(itemList.searchHistory)
    }
}

@BindingAdapter("recommendKeywordAdapter")
fun RecyclerView.bindRecommendKeywordAdapter(itemList: RecommendKeywordList) {
    val boundAdapter = this.adapter
    if (boundAdapter is RecommendKeywordAdapter) {
        boundAdapter.submitList(itemList.recommendKeyword)
    }
}