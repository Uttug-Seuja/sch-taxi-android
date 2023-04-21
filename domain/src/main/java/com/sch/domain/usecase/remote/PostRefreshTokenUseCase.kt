package com.sch.domain.usecase.remote

import com.sch.domain.NetworkResult
import com.sch.domain.model.ResultSearchKeyword
import com.sch.domain.model.Token
import com.sch.domain.repository.KakaoRepository
import com.sch.domain.repository.MainRepository
import retrofit2.Response
import javax.inject.Inject

class PostRefreshTokenUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(refreshToken: String): NetworkResult<Token> =
        repository.postRefreshToken(
            refreshToken = refreshToken
        )
}