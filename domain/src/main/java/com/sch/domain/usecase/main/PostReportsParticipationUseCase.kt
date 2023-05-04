package com.sch.domain.usecase.main

import com.sch.domain.NetworkResult
import com.sch.domain.model.ReportNotification
import com.sch.domain.repository.MainRepository
import javax.inject.Inject

class PostReportsParticipationUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(
        participationId: Int,
        reportReason: String,
        reportType: String
    ): NetworkResult<ReportNotification> =
        repository.postReportsParticipation(
            participationId = participationId,
            reportReason = reportReason,
            reportType = reportType
        )
}