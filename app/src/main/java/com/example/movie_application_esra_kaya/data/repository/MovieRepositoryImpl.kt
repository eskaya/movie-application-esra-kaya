import com.example.movie_application_esra_kaya.data.remote.models.request.MovieDetailDto
import com.example.movie_application_esra_kaya.data.remote.models.request.MovieListDto
import com.example.movie_application_esra_kaya.data.remote.services.MovieApi
import com.example.movie_application_esra_kaya.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi
) : MovieRepository {

    override suspend fun getMovieList(): MovieListDto {
        return api.getMovieList()
    }

    override suspend fun getMovieDetail(movie_id: Int): MovieDetailDto {
       return api.getMovieDetail(movie_id)
    }

}