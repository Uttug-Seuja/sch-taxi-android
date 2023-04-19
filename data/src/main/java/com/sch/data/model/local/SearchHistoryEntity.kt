package com.sch.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "searchHistory")
data class SearchHistoryEntity(
    val title: String
) {
    @PrimaryKey(autoGenerate = true)
    var searchHistoryIdx: Int = 0
}