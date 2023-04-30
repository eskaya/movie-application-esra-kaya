package com.eskaya.movie_application.data.remote.models.response


import com.eskaya.movie_application.data.remote.models.models.Cast
import com.eskaya.movie_application.data.remote.models.models.Crew

data class ActorsDto(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)