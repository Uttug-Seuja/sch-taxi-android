package com.sch.data.repository


import com.sch.data.api.handleApi
import com.sch.data.dao.SearchHistoryDao
import com.sch.data.model.toData
import com.sch.data.model.toDomain
import com.sch.domain.NetworkResult
import com.sch.domain.model.SearchHistory
import com.sch.domain.model.SearchHistoryList
import com.sch.domain.repository.SearchHistoryRepository
import javax.inject.Inject

class SearchHistoryRepositoryImpl @Inject constructor(
    private val searchHistoryDao: SearchHistoryDao
) : SearchHistoryRepository {

    override suspend fun createSearchHistory(searchHistory: SearchHistory): NetworkResult<Unit> {
        return handleApi{ searchHistoryDao.createSearchHistory(searchHistory.toData()) }
    }

    override suspend fun getSearchHistoryList(): NetworkResult<SearchHistoryList> {
        return handleApi{searchHistoryDao.getSearchHistoryList().toDomain()}
    }

    override suspend fun deleteSearchHistory(searchHistoryIdx: Int): NetworkResult<Unit> {
        return handleApi{searchHistoryDao.deleteSearchHistory(searchHistoryIdx)}
    }

    override suspend fun deleteSearchHistoryList(): NetworkResult<Unit> {
        return handleApi{searchHistoryDao.deleteSearchHistoryList()}
    }
}