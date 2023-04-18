package com.eskaya.movie_application.domain.use_case

import com.eskaya.movie_application.domain.repository.MovieRepository
import com.eskaya.movie_application.data.remote.models.response.SearchDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import com.eskaya.movie_application.utils.Resource

class GetSearchUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(query: String): Flow<Resource<SearchDto>> = flow {
        try {
            emit(Resource.Loading())
            val movies = repository.getSearchResult(query)
            emit(Resource.Success(data = movies))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }


}