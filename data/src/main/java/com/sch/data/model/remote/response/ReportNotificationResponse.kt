package com.sch.data.model.remote.response

data class ReportNotificationResponse(
    val id: Int,
    val notification_id: Int,
    val report_reason: String,
    val description: String,
    val defendant_id: Int
)

//{
//    "id": 0,
//    "notification_id": 0,
//    "report_reason": "SPAM",
//    "description": "string",
//    "defendant_id": 0
//}