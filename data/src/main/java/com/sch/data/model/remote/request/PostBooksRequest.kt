package com.sch.data.model.remote.request

import com.google.gson.annotations.SerializedName

data class PostBooksRequest(
    @SerializedName("bookName") val bookName: String,
    @SerializedName("author") val author: String,
    @SerializedName("publisher") val publisher: String,
    @SerializedName("pageNumber") val pageNumber: String
)