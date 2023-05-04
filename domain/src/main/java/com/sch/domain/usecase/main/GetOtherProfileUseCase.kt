package com.sch.domain.usecase.main

import com.sch.domain.NetworkResult
import com.sch.domain.model.UserInfo
import com.sch.domain.repository.MainRepository
import javax.inject.Inject

class GetOtherProfileUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(userId: Int): NetworkResult<UserInfo> =
        repository.getOtherProfile(userId = userId)
}