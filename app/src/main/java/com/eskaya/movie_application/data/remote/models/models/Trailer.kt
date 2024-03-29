package com.eskaya.movie_application.data.remote.models.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Trailer(
    val id: String,
    @SerializedName("iso_3166_1")
    val iso31661: String,
    @SerializedName("iso_639_1")
    val iso6391: String,
    val key: String,
    val name: String,
    val official: Boolean,
    @SerializedName("published_at")
    val publishedAt: String,
    val site: String,
    val size: Int,
    val type: String
): Parcelable