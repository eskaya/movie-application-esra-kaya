package com.eskaya.movie_application.utils.extensions

import com.eskaya.movie_application.BuildConfig
import com.eskaya.movie_application.utils.Constants

fun String.toFullImageLink(): String = BuildConfig.POSTER_PATH + this