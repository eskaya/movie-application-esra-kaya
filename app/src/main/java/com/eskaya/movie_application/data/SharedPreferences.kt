package com.eskaya.movie_application.data

import android.content.Context
import android.content.SharedPreferences

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
        return sharedPreferences.getString("THEME_MODE", "dark")
    }
}



