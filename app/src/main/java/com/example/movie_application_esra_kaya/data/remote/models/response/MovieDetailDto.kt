package com.example.movie_application_esra_kaya.data.remote.models.response

import com.example.movie_application_esra_kaya.data.remote.models.models.Genre
import com.example.movie_application_esra_kaya.data.remote.models.models.ProductionCompany
import com.example.movie_application_esra_kaya.data.remote.models.models.ProductionCountry
import com.example.movie_application_esra_kaya.data.remote.models.models.SpokenLanguage
import com.example.movie_application_esra_kaya.data.remote.models.models.BelongsToCollection
import com.google.gson.annotations.SerializedName

data class MovieDetailDto(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    @SerializedName("imdb_id")
    val imdbId: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry>,
    @SerializedName("release_date")
    val releaseDate: String,
    val revenue: Long,
    val runtime: Int,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
)