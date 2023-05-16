package com.sch.domain.usecase.main

import com.sch.domain.NetworkResult
import com.sch.domain.model.Participation
import com.sch.domain.repository.MainRepository
import javax.inject.Inject

class GetParticipationUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(id: Int): NetworkResult<Participation> =
        repository.getParticipation(id = id)
}