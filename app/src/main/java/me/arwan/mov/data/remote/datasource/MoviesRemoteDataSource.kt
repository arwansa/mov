package me.arwan.mov.data.remote.datasource

import me.arwan.mov.models.Cast
import me.arwan.mov.models.Genre
import me.arwan.mov.models.Movie
import me.arwan.mov.models.Review
import me.arwan.mov.models.Video

interface MoviesRemoteDataSource {
    suspend fun getGenres(): List<Genre>
    suspend fun getDiscoverMoviesByGenre(withGenre: Long, page: Int): List<Movie>
    suspend fun getMovieReviews(movieId: Long, page: Int): List<Review>
    suspend fun getVideos(movieId: Long): List<Video>
    suspend fun getMoviesForSearch(query: String): List<Movie>
    suspend fun getCreditsToMovie(movieId: Long): List<Cast>
}