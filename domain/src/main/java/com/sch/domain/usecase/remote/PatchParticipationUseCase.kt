package com.sch.domain.usecase.remote

import com.sch.domain.NetworkResult
import com.sch.domain.model.Participation
import com.sch.domain.model.Reservation
import com.sch.domain.model.ResultSearchKeyword
import com.sch.domain.model.Token
import com.sch.domain.repository.KakaoRepository
import com.sch.domain.repository.MainRepository
import retrofit2.Response
import javax.inject.Inject

class PatchParticipationUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(
        participationId: Int,
        seatPosition: String
    ): NetworkResult<Participation> =
        repository.patchParticipation(
            participationId = participationId,
            seatPosition = seatPosition
        )
}