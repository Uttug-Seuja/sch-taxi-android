package com.sch.domain.repository


import com.sch.domain.NetworkResult
import com.sch.domain.model.SearchHistory
import com.sch.domain.model.SearchHistoryList

interface SearchHistoryRepository {
    suspend fun createSearchHistory(searchHistory: SearchHistory): NetworkResult<Unit>

    suspend fun getSearchHistoryList(): NetworkResult<SearchHistoryList>

//    suspend fun searchPost(query: String) : NetworkResult<List<SearchHistory>>
//
//    suspend fun getTodo(todoIdx: Int): NetworkResult<SearchHistory>

    suspend fun deleteSearchHistory(searchHistoryIdx: Int): NetworkResult<Unit>

    suspend fun deleteSearchHistoryList(): NetworkResult<Unit>

//    suspend fun updatePostLocal(todoIdx: Int, title: String?, body: String?): NetworkResult<Unit>
//
//    suspend fun isCheckTodo(todoIdx: Int): NetworkResult<Unit>
}