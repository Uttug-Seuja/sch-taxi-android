package com.sch.domain.usecase.local

import com.sch.domain.NetworkResult
import com.sch.domain.repository.SearchHistoryRepository
import javax.inject.Inject

class DeleteSearchHistoryUseCase @Inject constructor(
    private val repository: SearchHistoryRepository
) {
    suspend operator fun invoke(searchHistoryIdx: Int): NetworkResult<Unit> =
        repository.deleteSearchHistory(searchHistoryIdx)
}