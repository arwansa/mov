package me.arwan.mov.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import me.arwan.mov.BuildConfig
import me.arwan.mov.models.response.movie.MovieDTO
import java.text.SimpleDateFormat
import java.util.Locale

@Parcelize
data class Movie(
    val title: String,
    val originalTitle: String,
    val description: String,
    val releaseDate: Long?,
    val imgMovie: String,
    val imgCover: String,
    val voteAverage: Double,
    val id: Long = 0,
) : Parcelable {
    companion object {
        fun fromResponse(movieDTO: MovieDTO): Movie {
            val simpleFormat = SimpleDateFormat("yyyy-mm-dd", Locale.getDefault())
            val date = if (movieDTO.releaseDate.isNotBlank()) {
                simpleFormat.parse(movieDTO.releaseDate)
            } else {
                null
            }
            return Movie(
                title = movieDTO.title,
                originalTitle = movieDTO.originalTitle,
                imgCover = "${BuildConfig.PREFIX_IMAGE_URL}${movieDTO.backdropPath}",
                imgMovie = "${BuildConfig.PREFIX_IMAGE_URL}${movieDTO.posterPath}",
                description = movieDTO.overview,
                releaseDate = date?.time,
                voteAverage = movieDTO.voteAverage,
                id = movieDTO.id
            )
        }
    }
}