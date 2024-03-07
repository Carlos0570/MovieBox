package com.example.moviebox.core.data.dataClasses

import com.google.gson.annotations.SerializedName

data class MovieBoxResponse<T>(
    @SerializedName("page")
    var page: Int? = null,
    @SerializedName("results")
    var results: List<T>? = null,
    @SerializedName("total_pages")
    var totalPages: Int? = null,
    @SerializedName("total_results")
    var totalResults: Int? = null
)