package com.eskaya.movie_application.data.remote.models.models


import com.google.gson.annotations.SerializedName

data class Crew(
    val adult: Boolean,
    @SerializedName("credit_id")
    val creditİd: String,
    val department: String,
    val gender: Int,
    val id: Int,
    val job: String,
    @SerializedName("known_for_department")
    val knownForDepartment: String,
    val name: String,
    @SerializedName("original_name")
    val originalName: String,
    val popularity: Double,
    @SerializedName("profile_path")
    val profilePath: String
)