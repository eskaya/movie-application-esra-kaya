package com.eskaya.movie_application.domain.use_case

import com.eskaya.movie_application.data.remote.models.response.ActorsDto
import com.eskaya.movie_application.domain.repository.MovieRepository
import com.eskaya.movie_application.utils.Resource
import com.eskaya.movie_application.utils.extensions.handleError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetActorsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(movieId: Int): Flow<Resource<ActorsDto>> = flow {
        try {
            emit(Resource.Loading())
            val actorList = repository.getMovieActors(movieId)
            emit(Resource.Success(data = actorList))
        } catch (e: HttpException) {
            emit(Resource.Error(e.handleError()))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}



