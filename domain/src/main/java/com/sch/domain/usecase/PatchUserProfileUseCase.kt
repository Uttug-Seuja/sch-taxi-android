package com.sch.domain.usecase

import com.sch.domain.NetworkResult
import com.sch.domain.model.UserInfo
import com.sch.domain.repository.MainRepository
import javax.inject.Inject

class PatchUserProfileUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(profilePath: String): NetworkResult<UserInfo> =
        repository.patchUserProfile(
            profilePath = profilePath
        )
}