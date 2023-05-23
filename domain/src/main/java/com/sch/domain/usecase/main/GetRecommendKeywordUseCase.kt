package com.sch.domain.usecase.main

import com.sch.domain.NetworkResult
import com.sch.domain.model.Keyword
import com.sch.domain.repository.MainRepository
import javax.inject.Inject

class GetRecommendKeywordUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(): NetworkResult<List<Keyword>> =
        repository.getRecommendKeyword()
}