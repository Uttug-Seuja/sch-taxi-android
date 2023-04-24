package com.sch.domain.usecase.remote

import com.sch.domain.NetworkResult
import com.sch.domain.model.IsRegistered
import com.sch.domain.model.UserProfile
import com.sch.domain.repository.MainRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(userId: Int): NetworkResult<UserProfile> =
        repository.getUserProfile(userId = userId)
}