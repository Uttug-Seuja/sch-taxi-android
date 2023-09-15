package com.sch.domain.usecase.main

import com.sch.domain.NetworkResult
import com.sch.domain.model.ImageUrl
import com.sch.domain.repository.MainRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class PostEmailUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(email : String, oauthProvider : String): NetworkResult<Unit> =
        repository.postEmail(email = email, oauthProvider = oauthProvider)
}