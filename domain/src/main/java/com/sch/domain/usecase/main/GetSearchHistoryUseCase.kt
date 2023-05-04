package com.sch.domain.usecase.main

import com.sch.domain.NetworkResult
import com.sch.domain.model.ResultSearchKeyword
import com.sch.domain.model.SearchHistory
import com.sch.domain.model.SearchHistoryList
import com.sch.domain.repository.KakaoRepository
import com.sch.domain.repository.SearchHistoryRepository
import retrofit2.Response
import javax.inject.Inject

class GetSearchHistoryUseCase @Inject constructor(
    private val repository: SearchHistoryRepository
) {
    suspend operator fun invoke(): NetworkResult<SearchHistoryList> = repository.getSearchHistoryList()
}