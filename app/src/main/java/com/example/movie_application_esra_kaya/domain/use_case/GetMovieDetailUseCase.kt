package com.example.movie_application_esra_kaya.domain.use_case

import com.example.movie_application_esra_kaya.data.remote.models.request.MovieDetailDto
import com.example.movie_application_esra_kaya.domain.repository.MovieRepository
import com.example.movie_application_esra_kaya.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(movie_id: Int): Flow<Resource<MovieDetailDto>> = flow {
        try {
            emit(Resource.Loading())
            val movieDetail = repository.getMovieDetail(movie_id)
            emit(Resource.Success(data = movieDetail))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}