package com.sch.domain.usecase.main

import com.sch.domain.NetworkResult
import com.sch.domain.model.PagingChat
import com.sch.domain.repository.MainRepository
import javax.inject.Inject

class PostChatUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(
        participationId: Int,
        message: String,
        writer: String,
        cursor: String
    ): NetworkResult<PagingChat> =
        repository.postChat(
            participationId = participationId,
            message = message,
            writer = writer,
            cursor = cursor
        )
}