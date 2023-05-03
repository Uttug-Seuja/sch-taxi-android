package com.sch.domain.usecase

import com.sch.domain.NetworkResult
import com.sch.domain.model.PagingReservations
import com.sch.domain.model.Reservation
import com.sch.domain.model.ResultSearchKeyword
import com.sch.domain.model.Token
import com.sch.domain.repository.KakaoRepository
import com.sch.domain.repository.MainRepository
import retrofit2.Response
import javax.inject.Inject

class GetReservationSearchUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(
        keyword: String,
        page: Int,
        size: Int
    ): NetworkResult<PagingReservations> =
        repository.getReservationSearch(
            keyword = keyword,
            page = page,
            size = size,
        )
}