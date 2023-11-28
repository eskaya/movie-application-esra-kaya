package com.eskaya.movie_application.data.remote.models.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "movie_table")
data class MovieItem(

    @ColumnInfo(name = "adult")
    val adult: Boolean,

    @ColumnInfo(name = "backdropPath")
    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @ColumnInfo(name = "genreIds")
    @SerializedName("genre_ids")
    val genreIds: List<Int>,

    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "originalLanguage")
    @SerializedName("original_language")
    val originalLanguage: String,

    @ColumnInfo(name = "originalTitle")
    @SerializedName("original_title")
    val originalTitle: String,

    @ColumnInfo(name = "overview")
    val overview: String,

    @ColumnInfo(name = "popularity")
    val popularity: Double,

    @ColumnInfo(name = "posterPath")
    @SerializedName("poster_path")
    val posterPath: String?,

    @ColumnInfo(name = "releaseDate")
    @SerializedName("release_date")
    val releaseDate: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "video")
    val video: Boolean,

    @ColumnInfo(name = "voteAverage")
    @SerializedName("vote_average")
    val voteAverage: Double,

    @ColumnInfo(name = "voteCount")
    @SerializedName("vote_count")
    val voteCount: Int
) {
    @PrimaryKey(autoGenerate = true)
    var dbId: Int = 0
}