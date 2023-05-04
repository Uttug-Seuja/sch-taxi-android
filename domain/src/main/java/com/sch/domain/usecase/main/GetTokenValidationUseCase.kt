package com.sch.domain.usecase.main

import com.sch.domain.NetworkResult
import com.sch.domain.model.IsRegistered
import com.sch.domain.repository.MainRepository
import javax.inject.Inject

class GetTokenValidationUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(idToken: String, provider: String): NetworkResult<IsRegistered> =
        repository.getTokenValidation(
            idToken = idToken,
            provider = provider
        )
}