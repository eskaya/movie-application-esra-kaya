package com.eskaya.movie_application.utils.extensions


import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response



fun handleError(call: Call<ResponseBody?>?, response: Response<ResponseBody?>) {
    if (response.isSuccessful) {
        // Do your success stuff...
    } else {
        try {
            val jObjError = JSONObject(response.errorBody()?.string())
           // Toast.makeText(getContext(), jObjError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG
            println(jObjError.getJSONObject("error").getString("message"))

        } catch (e: Exception) {
          //  Toast.makeText(getContext(), e.message, Toast.LENGTH_LONG).show()
            println( e.message)
        }
    }
}
