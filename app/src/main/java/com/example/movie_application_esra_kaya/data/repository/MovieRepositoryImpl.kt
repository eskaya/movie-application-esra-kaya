import com.example.movie_application_esra_kaya.data.remote.models.response.MovieDetailDto
import com.example.movie_application_esra_kaya.data.remote.models.response.MovieListDto
import com.example.movie_application_esra_kaya.data.remote.models.response.SearchDto
import com.example.movie_application_esra_kaya.data.remote.services.MovieApi
import com.example.movie_application_esra_kaya.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi
) : MovieRepository {

    override suspend fun getMovieList(): MovieListDto {
        return api.getMovieList()
    }

    override suspend fun getMovieDetail(movieId: Int): MovieDetailDto {
        return api.getMovieDetail(movieId)
    }

    override suspend fun getSearchResult(query: String): SearchDto {
        return api.getSearchResult(query)
    }

}