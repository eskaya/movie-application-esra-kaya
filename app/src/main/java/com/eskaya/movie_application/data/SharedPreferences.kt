package com.eskaya.movie_application.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AppPreferences private constructor(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "MyAppPrefs"
        private var instance: AppPreferences? = null

        fun getInstance(context: Context): AppPreferences {
            if (instance == null) {
                instance = AppPreferences(context)
            }
            return instance!!
        }
    }


    fun setThemeMode(value: String) {
        sharedPreferences.edit().putString("THEME_MODE", value).apply()
    }

    fun getThemeMode(): String? {
        return sharedPreferences.getString("THEME_MODE", null)
    }

    fun saveNotifications(data: Any) {
        val dataList = mutableListOf<Any>()
        dataList.add(data)

        val gson = Gson()
        val json = gson.toJson(dataList)

        val editor = sharedPreferences.edit()
        editor.putString("data_key", json)
        editor.apply()
    }

    fun getNotifications(): Any {
        val json = sharedPreferences.getString("data_key", "")
        val gson = Gson()
        val listType = object : TypeToken<List<Any>>() {}.type

        val dataList = if (json.isNullOrEmpty()) {
             mutableListOf<Any>()
        } else {
             gson.fromJson(json, listType)
        }

        return dataList
    }
}



