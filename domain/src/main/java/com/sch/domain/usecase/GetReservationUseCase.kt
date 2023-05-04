package com.sch.domain.usecase

import com.sch.domain.NetworkResult
import com.sch.domain.model.PagingReservation
import com.sch.domain.repository.MainRepository
import javax.inject.Inject

class GetReservationUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(
        page: Int,
        size: Int
    ): NetworkResult<PagingReservation> =
        repository.getReservation(
            page = page,
            size = size,
        )
}