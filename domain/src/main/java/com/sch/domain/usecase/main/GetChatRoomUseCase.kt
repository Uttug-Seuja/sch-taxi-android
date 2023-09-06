package com.sch.domain.usecase.main

import com.sch.domain.NetworkResult
import com.sch.domain.model.ChatRoom
import com.sch.domain.repository.MainRepository
import javax.inject.Inject

class GetChatRoomUseCase @Inject constructor(
    private val repository: MainRepository,
) {
    suspend operator fun invoke(): NetworkResult<List<ChatRoom>> =
        repository.getChatRoom()
}