package com.eskaya.movie_application.utils.extensions


import org.json.JSONObject
import retrofit2.HttpException

fun HttpException.handleError(): String {

//TODO --> status_message alınamıyor
    var errorMessage = ""
    errorMessage = try {
        val jObjError = JSONObject(this.response()?.errorBody()?.string())
       jObjError.getJSONObject("status_message").getString("status_message")

    } catch (e: Exception) {
        e.message.toString()
    }
    return errorMessage
}
