package com.eskaya.movie_application.utils.extensions

val Double.oneDigit: String get() = String.format("%.1f", this)