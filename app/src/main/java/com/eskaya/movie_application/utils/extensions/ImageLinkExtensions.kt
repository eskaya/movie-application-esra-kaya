package com.eskaya.movie_application.utils.extensions

import com.eskaya.movie_application.BuildConfig

fun String.toFullImageLink(): String = BuildConfig.POSTER_PATH + this