package com.eskaya.movie_application.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.eskaya.movie_application.data.db.services.MovieDao
import com.eskaya.movie_application.data.remote.models.models.MovieItem
import dagger.hilt.android.HiltAndroidApp


@Database(entities = [MovieItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        private var lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, AppDatabase::class.java, "movie_database"
        ).build()
    }

}