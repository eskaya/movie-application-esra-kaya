package com.eskaya.movie_application.data.remote.models.response


import com.eskaya.movie_application.data.remote.models.models.Trailer

data class TrailersDto(
    val id: Int,
    val results: List<Trailer>
)