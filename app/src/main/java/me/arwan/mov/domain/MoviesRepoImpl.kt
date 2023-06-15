package me.arwan.mov.domain

import me.arwan.mov.data.local.datasource.MovieLocalDataSource
import me.arwan.mov.data.remote.datasource.MoviesRemoteDataSource
import me.arwan.mov.models.Cast
import me.arwan.mov.models.Movie
import kotlinx.coroutines.flow.Flow
import me.arwan.mov.models.Genre
import me.arwan.mov.models.Review
import me.arwan.mov.models.Video
import javax.inject.Inject

class MoviesRepoImpl @Inject constructor(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
    private val moviesLocalDataSource: MovieLocalDataSource,
) : MoviesRepository {
    override val listGenres: Flow<List<Genre>> = moviesLocalDataSource.listGenres

    override suspend fun upsertGenres(): Int {
        val genres = moviesRemoteDataSource.getGenres()
        if (genres.isNotEmpty()) moviesLocalDataSource.upsertGenres(genres)
        return genres.size
    }

    override suspend fun getDiscoverMoviesByGenre(withGenre: Long, page: Int): List<Movie> =
        moviesRemoteDataSource.getDiscoverMoviesByGenre(withGenre, page)

    override suspend fun getMovieReviews(movieId: Long, page: Int): List<Review> =
        moviesRemoteDataSource.getMovieReviews(movieId, page)

    override suspend fun getVideos(movieId: Long): List<Video> =
        moviesRemoteDataSource.getVideos(movieId)

    override suspend fun getCastFromMovie(movieId: Long): List<Cast> =
        moviesRemoteDataSource.getCreditsToMovie(movieId)

    override suspend fun getMoviesForSearch(query: String): List<Movie> =
        moviesRemoteDataSource.getMoviesForSearch(query)

}