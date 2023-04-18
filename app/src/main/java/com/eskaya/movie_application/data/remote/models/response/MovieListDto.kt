package com.eskaya.movie_application.data.remote.models.response
import com.eskaya.movie_application.data.remote.models.models.MovieItem
import com.google.gson.annotations.SerializedName

data class MovieListDto(
    val page: Int,
    val results: List<MovieItem>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)