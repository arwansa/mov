package me.arwan.mov.data.remote.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.arwan.mov.core.utils.ExceptionManager
import me.arwan.mov.core.utils.InternetCheck
import me.arwan.mov.data.remote.services.MoviesServices
import me.arwan.mov.models.Cast
import me.arwan.mov.models.Movie
import kotlinx.coroutines.withTimeoutOrNull
import me.arwan.mov.data.remote.pagingsource.MoviePagingSource
import me.arwan.mov.models.Genre
import me.arwan.mov.models.Review
import me.arwan.mov.models.Video

class MoviesRemoteDataSourceImpl(
    private val moviesServices: MoviesServices,
) : MoviesRemoteDataSource {

    override suspend fun getGenres(): List<Genre> = callApiWithTimeout {
        moviesServices.getGenres()
    }.genres.map(Genre::fromResponse)

    override fun getAllDiscoverMoviesByGenre(withGenre: Long): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                MoviePagingSource(
                    response = { nextPage ->
                        callApiWithTimeout {
                            moviesServices.getDiscoverMoviesByGenre(
                                withGenres = withGenre,
                                sortBy = "popularity.desc",
                                page = nextPage
                            )
                        }
                    }
                )
            }
        ).flow.map { pagingData ->
            pagingData.map(Movie::fromResponse)
        }

    override fun getAllMovieReviews(movieId: Long): Flow<PagingData<Review>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                MoviePagingSource(
                    response = { nextPage ->
                        callApiWithTimeout {
                            moviesServices.getMovieReviews(
                                movieId = movieId,
                                page = nextPage
                            )
                        }
                    }
                )
            }
        ).flow.map { pagingData ->
            pagingData.map(Review::fromResponse)
        }

    override suspend fun getVideos(movieId: Long): List<Video> = callApiWithTimeout {
        moviesServices.getVideos(movieId)
    }.results.map(Video::fromResponse)

    override suspend fun getCreditsToMovie(movieId: Long): List<Cast> = callApiWithTimeout {
        moviesServices.getCredits(movieId)
    }.cast.map(Cast::fromResponse)

    override suspend fun getMoviesForSearch(query: String): List<Movie> = callApiWithTimeout {
        moviesServices.getResultForSearch(
            page = 1,
            query = query
        ).results.map(Movie::fromResponse)
    }

    private suspend fun <T> callApiWithTimeout(
        timeout: Long = 3_000,
        callApi: suspend () -> T,
    ): T {
        if (!InternetCheck.isNetworkAvailable()) throw Exception(ExceptionManager.NO_NETWORK_ERROR)
        return withTimeoutOrNull(timeout) { callApi() }!!
    }

    private companion object {
        const val PAGE_SIZE = 20
    }
}