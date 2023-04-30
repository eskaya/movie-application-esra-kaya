package com.eskaya.movie_application.data.remote.models.models


import com.google.gson.annotations.SerializedName

data class Cast(
    val adult: Boolean,
    @SerializedName("cast_id")
    val castİd: Int,
    val character: String,
    @SerializedName("credit_id")
    val creditİd: String,
    val gender: Int,
    val id: Int,
    @SerializedName("known_for_department")
    val knownForDepartment: String,
    val name: String,
    val order: Int,
    @SerializedName("original_name")
    val originalName: String,
    val popularity: Double,
    @SerializedName("profile_path")
    val profilePath: String?
)