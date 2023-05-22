package com.sch.domain.usecase.main

import com.sch.domain.NetworkResult
import com.sch.domain.model.AssetRandom
import com.sch.domain.model.UserInfo
import com.sch.domain.repository.MainRepository
import javax.inject.Inject

class GetAssetRandomUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(): NetworkResult<AssetRandom> =
        repository.getAssetRandom()
}