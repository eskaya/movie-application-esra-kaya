package com.eskaya.movie_application.domain.use_case

import com.eskaya.movie_application.data.remote.models.response.TrailersDto
import com.eskaya.movie_application.domain.repository.MovieRepository
import com.eskaya.movie_application.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import com.eskaya.movie_application.utils.extensions.handleError

class GetMovieTrailersUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(movieId: Int): Flow<Resource<TrailersDto>> = flow {
        try {
            emit(Resource.Loading())
            val trailers = repository.getMovieTrailers(movieId)
            emit(Resource.Success(data = trailers))
        } catch (e: HttpException) {
            emit(Resource.Error(e.handleError()))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}



