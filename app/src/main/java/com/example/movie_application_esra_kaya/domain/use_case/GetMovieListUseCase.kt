package com.example.movie_application_esra_kaya.domain.use_case

import com.example.movie_application_esra_kaya.domain.repository.MovieRepository
import com.example.movie_application_esra_kaya.data.remote.dto.MovieListDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import com.example.movie_application_esra_kaya.utils.Resource

class GetMovieListUseCase @Inject constructor(
private val repository: MovieRepository
) {
    operator fun invoke(): Flow<Resource<List<MovieListDto>>> = flow {
        try {
            emit(Resource.Loading<List<MovieListDto>>())
            val coins = repository.getMovieList()
            emit(Resource.Success<List<MovieListDto>>(coins))
        } catch(e: HttpException) {
            emit(Resource.Error<List<MovieListDto>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<List<MovieListDto>>("Couldn't reach server. Check your internet connection."))
        }
    }
}