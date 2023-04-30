package com.eskaya.movie_application.domain.use_case

import com.eskaya.movie_application.data.remote.models.response.MovieDetailDto
import com.eskaya.movie_application.domain.repository.MovieRepository
import com.eskaya.movie_application.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import com.eskaya.movie_application.utils.extensions.handleError
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class GetMovieDetailUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(movieId: Int): Flow<Resource<MovieDetailDto>> = flow {
        try {
            emit(Resource.Loading())
            val movieDetail = repository.getMovieDetail(movieId)
            emit(Resource.Success(data = movieDetail))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message() ?: "An unexpected error occured"))
           // emit(Resource.Error(e.handleError()))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}



