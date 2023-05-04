package com.sch.domain.usecase.main

import com.sch.domain.NetworkResult
import com.sch.domain.repository.SearchHistoryRepository
import javax.inject.Inject

class DeleteSearchHistoryListUseCase @Inject constructor(
    private val repository: SearchHistoryRepository
) {
    suspend operator fun invoke(): NetworkResult<Unit> = repository.deleteSearchHistoryList()
}