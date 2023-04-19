package com.sch.domain.usecase.local

import com.sch.domain.NetworkResult
import com.sch.domain.model.SearchHistory
import com.sch.domain.repository.SearchHistoryRepository
import javax.inject.Inject

class CreateSearchHistoryUseCase @Inject constructor(
    private val repository: SearchHistoryRepository
) {
    suspend operator fun invoke(searchHistory: SearchHistory): NetworkResult<Unit> =
        repository.createSearchHistory(searchHistory)
}