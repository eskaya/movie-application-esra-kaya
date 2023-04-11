package com.example.movie_application_esra_kaya.domain.use_case

import com.example.movie_application_esra_kaya.domain.repository.MovieRepository
import com.example.movie_application_esra_kaya.data.remote.models.response.MovieListDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import com.example.movie_application_esra_kaya.utils.Resource

class GetPopularMovieListUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<Resource<MovieListDto>> = flow {
        try {
            emit(Resource.Loading())
            val movies = repository.getMovieList()
            emit(Resource.Success(data = movies))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }


}