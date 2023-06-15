package me.arwan.mov.domain

import me.arwan.mov.models.Cast
import me.arwan.mov.models.Movie
import kotlinx.coroutines.flow.Flow
import me.arwan.mov.models.Genre
import me.arwan.mov.models.Review
import me.arwan.mov.models.Video

interface MoviesRepository {
    val listGenres: Flow<List<Genre>>

    suspend fun upsertGenres(): Int
    suspend fun getDiscoverMoviesByGenre(withGenre: Long, page: Int): List<Movie>
    suspend fun getMovieReviews(movieId: Long, page: Int): List<Review>
    suspend fun getVideos(movieId: Long): List<Video>
    suspend fun getCastFromMovie(movieId: Long): List<Cast>
    suspend fun getMoviesForSearch(query: String): List<Movie>
}