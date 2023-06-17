package me.arwan.mov.data.remote.services

import me.arwan.mov.models.response.PagingResponse
import me.arwan.mov.models.response.cast.CastResponse
import me.arwan.mov.models.response.genre.GenreResponse
import me.arwan.mov.models.response.movie.MovieDTO
import me.arwan.mov.models.response.review.ReviewDTO
import me.arwan.mov.models.response.trailer.VideoDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesServices {

    @GET("genre/movie/list")
    suspend fun getGenres(): GenreResponse

    @GET("discover/movie")
    suspend fun getDiscoverMoviesByGenre(
        @Query("with_genres") withGenres: Long,
        @Query("sort_by") sortBy: String,
        @Query("page") page: Int
    ): PagingResponse<MovieDTO>

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Long,
        @Query("page") page: Int
    ): PagingResponse<ReviewDTO>

    @GET("movie/{movie_id}/videos")
    suspend fun getVideos(
        @Path("movie_id") movieId: Long
    ): PagingResponse<VideoDTO>

    @GET("movie/{movie_id}/credits")
    suspend fun getCredits(
        @Path("movie_id") movieId: Long
    ): CastResponse

    @GET("search/movie")
    suspend fun getResultForSearch(
        @Query("page") page: Int,
        @Query("query") query: String,
    ): PagingResponse<MovieDTO>
}