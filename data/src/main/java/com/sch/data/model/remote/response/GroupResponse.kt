package com.sch.data.model.remote.response


data class GroupResponse(
    val background_image_path: String,
    val description: String,
    val group_id: Int,
    val group_type: String,
    val ihost: Boolean,
    val public_access: Boolean,
    val thumbnail_path: String,
    val title: String
)