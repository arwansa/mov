package me.arwan.mov.data.remote.datasource

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.arwan.mov.models.Cast
import me.arwan.mov.models.Genre
import me.arwan.mov.models.Movie
import me.arwan.mov.models.Review
import me.arwan.mov.models.Video

interface MoviesRemoteDataSource {
    suspend fun getGenres(): List<Genre>
    fun getAllDiscoverMoviesByGenre(withGenre: Long): Flow<PagingData<Movie>>
    fun getAllMovieReviews(movieId: Long): Flow<PagingData<Review>>
    suspend fun getVideos(movieId: Long): List<Video>
    suspend fun getMoviesForSearch(query: String): List<Movie>
    suspend fun getCreditsToMovie(movieId: Long): List<Cast>
}