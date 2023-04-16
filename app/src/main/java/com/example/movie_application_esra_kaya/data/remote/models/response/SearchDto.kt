package com.example.movie_application_esra_kaya.data.remote.models.response

import com.example.movie_application_esra_kaya.data.remote.models.models.MovieItem
import com.google.gson.annotations.SerializedName

data class SearchDto(
    val page: Int,
    val results: List<MovieItem>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)