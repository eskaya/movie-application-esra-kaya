import com.eskaya.movie_application.data.remote.models.response.MovieDetailDto
import com.eskaya.movie_application.data.remote.models.response.MovieListDto
import com.eskaya.movie_application.data.remote.models.response.SearchDto
import com.eskaya.movie_application.data.remote.services.MovieApi
import com.eskaya.movie_application.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi
) : MovieRepository {

    override suspend fun getMovieList(type: String): MovieListDto {
        return api.getMovieList(type)
    }

    override suspend fun getMovieDetail(movieId: Int): MovieDetailDto {
        return api.getMovieDetail(movieId)
    }

    override suspend fun getSearchResult(query: String): SearchDto {
        return api.getSearchResult(query)
    }

}