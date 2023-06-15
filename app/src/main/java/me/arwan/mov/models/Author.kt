package me.arwan.mov.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import me.arwan.mov.BuildConfig
import me.arwan.mov.models.response.review.AuthorDTO

@Parcelize
data class Author(
    val username: String,
    val avatarPath: String? = null,
    val rating: Double
) : Parcelable {
    companion object {
        fun fromResponse(authorDTO: AuthorDTO): Author {
            val urlImg = authorDTO.avatarPath?.let {
                "${BuildConfig.PREFIX_IMAGE_URL}$it"
            }
            return Author(
                username = authorDTO.username,
                avatarPath = urlImg,
                rating = authorDTO.rating
            )
        }
    }
}