package com.eskaya.movie_application.data.db.services

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.eskaya.movie_application.data.remote.models.models.MovieItem

@Dao
interface MovieDao {

    @Insert
    //bu istek bize primaryKey olarak işaretlediğimiz alanı bir dizi içerisinde döner, vararg ile kaç tane ekleneceğini belirtmemize gerek olmaz
    fun insertAll(vararg movie:MovieItem) : List<Long>

    @Insert
    fun insert(movie: MovieItem) //bu şekilde de ekleme işlemini yapabiliriz

    @Query("SELECT * FROM movie_table WHERE dbId IN (:id)")
    fun getMovie(id: Int): MovieItem

    @Query("SELECT * FROM movie_table")
    fun getMovies(): List<MovieItem>
    @Delete
    fun deleteMovie(movie: MovieItem)
}