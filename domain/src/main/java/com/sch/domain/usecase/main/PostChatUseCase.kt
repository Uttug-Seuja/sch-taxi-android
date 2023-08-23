package com.sch.domain.usecase.main

import com.sch.domain.NetworkResult
import com.sch.domain.model.PagingChat
import com.sch.domain.repository.MainRepository
import javax.inject.Inject

class PostChatUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(
        reservationId: Int,
        message: String,
        writer: String,
        cursor: String
    ): NetworkResult<PagingChat> =
        repository.postChat(
            reservationId = reservationId,
            message = message,
            writer = writer,
            cursor = cursor
        )
}