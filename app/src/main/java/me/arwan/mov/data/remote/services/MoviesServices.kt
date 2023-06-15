package me.arwan.mov.data.remote.services

import me.arwan.mov.models.response.cast.CastResponse
import me.arwan.mov.models.response.genre.GenreResponse
import me.arwan.mov.models.response.movie.MovieResponse
import me.arwan.mov.models.response.review.ReviewResponse
import me.arwan.mov.models.response.trailer.VideoResponse
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
    ): MovieResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Long,
        @Query("page") page: Int
    ): ReviewResponse

    @GET("movie/{movie_id}/videos")
    suspend fun getVideos(
        @Path("movie_id") movieId: Long
    ): VideoResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getCredits(
        @Path("movie_id") movieId: Long
    ): CastResponse

    @GET("search/movie")
    suspend fun getResultForSearch(
        @Query("page") page: Int,
        @Query("query") query: String,
    ): MovieResponse
}