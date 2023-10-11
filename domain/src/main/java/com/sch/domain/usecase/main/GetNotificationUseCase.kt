package com.sch.domain.usecase.main

import com.sch.domain.NetworkResult
import com.sch.domain.model.NotificationList
import com.sch.domain.repository.MainRepository
import javax.inject.Inject

class GetNotificationUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(page: Int, size: Int): NetworkResult<NotificationList> =
        repository.getNotification(page = page, size = size)
}