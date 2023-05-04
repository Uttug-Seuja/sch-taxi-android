package com.sch.domain.usecase.main

import com.sch.domain.NetworkResult
import com.sch.domain.model.PagingReservation
import com.sch.domain.repository.MainRepository
import javax.inject.Inject

class GetReservationSearchUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(
        keyword: String,
        page: Int,
        size: Int
    ): NetworkResult<PagingReservation> =
        repository.getReservationSearch(
            keyword = keyword,
            page = page,
            size = size,
        )
}