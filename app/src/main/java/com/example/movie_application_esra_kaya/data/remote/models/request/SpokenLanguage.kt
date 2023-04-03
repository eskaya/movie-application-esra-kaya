package com.example.movie_application_esra_kaya.data.remote.models.request

import com.google.gson.annotations.SerializedName

data class SpokenLanguage(
    @SerializedName("english_name")
    val englishName: String,
    val iso_639_1: String,
    val name: String
)