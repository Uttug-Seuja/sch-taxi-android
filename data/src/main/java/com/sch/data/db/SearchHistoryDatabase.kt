package com.sch.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sch.data.dao.SearchHistoryDao
import com.sch.data.model.local.SearchHistoryEntity

@Database(
    entities = [SearchHistoryEntity::class],
    version = 2,
    exportSchema = false
)
abstract class SearchHistoryDatabase : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao
}