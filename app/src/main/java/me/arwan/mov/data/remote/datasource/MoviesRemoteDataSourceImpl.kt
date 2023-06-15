package me.arwan.mov.data.remote.datasource

import me.arwan.mov.core.utils.ExceptionManager
import me.arwan.mov.core.utils.InternetCheck
import me.arwan.mov.data.remote.services.MoviesServices
import me.arwan.mov.models.Cast
import me.arwan.mov.models.Movie
import kotlinx.coroutines.withTimeoutOrNull
import me.arwan.mov.models.Genre
import me.arwan.mov.models.Review
import me.arwan.mov.models.Video

class MoviesRemoteDataSourceImpl(
    private val moviesServices: MoviesServices,
) : MoviesRemoteDataSource {

    override suspend fun getGenres(): List<Genre> = callApiWithTimeout {
        moviesServices.getGenres()
    }.genres.map(Genre::fromResponse)

    override suspend fun getDiscoverMoviesByGenre(withGenre: Long, page: Int): List<Movie> =
        callApiWithTimeout {
            moviesServices.getDiscoverMoviesByGenre(
                withGenres = withGenre,
                sortBy = "popularity.desc",
                page = page
            )
        }.results.map(Movie::fromResponse)

    override suspend fun getMovieReviews(movieId: Long, page: Int): List<Review> =
        callApiWithTimeout {
            moviesServices.getMovieReviews(movieId, page)
        }.results.map(Review::fromResponse)

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
}