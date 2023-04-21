package com.sch.domain.usecase.remote

import com.sch.domain.NetworkResult
import com.sch.domain.model.UserProfile
import com.sch.domain.repository.MainRepository
import javax.inject.Inject

class PutUserProfileUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(nickname: String, profilePath: String): NetworkResult<UserProfile> =
        repository.putUserProfile(
            nickname = nickname,
            profilePath = profilePath
        )
}