package com.sch.domain.usecase.main

import com.sch.domain.NetworkResult
import com.sch.domain.model.MyReservation
import com.sch.domain.model.PagingReservation
import com.sch.domain.repository.MainRepository
import javax.inject.Inject

class GetUserReservationUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(): NetworkResult<MyReservation> = repository.getUserReservation(
    )
}