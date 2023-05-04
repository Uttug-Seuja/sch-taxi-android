package com.sch.domain.usecase.main

import com.sch.domain.NetworkResult
import com.sch.domain.model.ResultSearchKeyword
import com.sch.domain.model.Token
import com.sch.domain.repository.KakaoRepository
import com.sch.domain.repository.MainRepository
import retrofit2.Response
import javax.inject.Inject

class PostRegisterUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(
        idToken: String,
        provider: String,
        name: String,
        schoolNum: String,
        gender: String,
        profilePath: String
    ): NetworkResult<Token> =
        repository.postRegister(
            idToken = idToken,
            provider = provider,
            name = name,
            schoolNum = schoolNum,
            gender = gender,
            profilePath = profilePath
        )
}