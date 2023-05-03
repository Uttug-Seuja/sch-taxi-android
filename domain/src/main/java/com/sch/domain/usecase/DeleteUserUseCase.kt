package com.sch.domain.usecase

import com.sch.domain.NetworkResult
import com.sch.domain.repository.MainRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(oauthAccessToken: String): NetworkResult<Unit> =
        repository.deleteUser(
            oauthAccessToken = oauthAccessToken
        )
}