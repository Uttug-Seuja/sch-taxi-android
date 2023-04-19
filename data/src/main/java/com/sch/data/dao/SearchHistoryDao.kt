package com.sch.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sch.data.model.local.SearchHistoryEntity

@Dao
interface SearchHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createSearchHistory(searchHistoryEntity: SearchHistoryEntity)

    @Query("SELECT * FROM searchHistory ORDER BY searchHistoryIdx DESC")
    suspend fun getSearchHistoryList(): List<SearchHistoryEntity>

//    @Query("SELECT * FROM todo WHERE searchHistoryIdx = :idx ")
//    suspend fun getSearchHistory(idx: Int): SearchHistoryEntity

    @Query("DELETE FROM searchHistory WHERE searchHistoryIdx = :idx")
    suspend fun deleteSearchHistory(idx: Int)

    @Query("DELETE FROM searchHistory")
    suspend fun deleteSearchHistoryList()

//    @Query("UPDATE todo SET title = :title, body = :body WHERE todoIdx = :id")
//    suspend fun patchSearchHistory(id: Int, title: String?, body: String?)

//    @Query("SELECT DISTINCT * FROM todo WHERE title LIKE '%'||:query||'%' OR body LIKE '%'||:query||'%' ORDER BY todoIdx DESC")
//    suspend fun searchTodo(query: String): List<SearchHistoryEntity>

//    @Query("UPDATE todo SET isChecked = not isChecked  WHERE todoIdx = :id")
//    suspend fun isCheckTodo(id: Int)
}