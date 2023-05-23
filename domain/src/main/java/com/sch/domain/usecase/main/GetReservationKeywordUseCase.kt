package com.sch.domain.usecase.main

import com.sch.domain.NetworkResult
import com.sch.domain.model.*
import com.sch.domain.repository.KakaoRepository
import com.sch.domain.repository.MainRepository
import retrofit2.Response
import javax.inject.Inject

class GetReservationKeywordUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(
        word: String,
        page: Int,
        size: Int,
    ): NetworkResult<PagingReservationKeyword> =
        repository.getReservationKeyword(
            word = word,
            page = page,
            size = size,
        )
}