package com.eskaya.movie_application.domain.use_case

import com.eskaya.movie_application.domain.repository.MovieRepository
import com.eskaya.movie_application.data.remote.models.response.MovieListDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import com.eskaya.movie_application.utils.Resource
import com.eskaya.movie_application.utils.extensions.handleError

class GetMovieListUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(type: String): Flow<Resource<MovieListDto>> = flow {
        try {
            emit(Resource.Loading())
            val movies = repository.getMovieList(type)
            emit(Resource.Success(data = movies))
        } catch (e: HttpException) {
            emit(Resource.Error(e.handleError()))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }


}