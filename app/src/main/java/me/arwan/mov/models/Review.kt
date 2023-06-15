package me.arwan.mov.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import me.arwan.mov.models.response.review.ReviewDTO

@Parcelize
data class Review(
    val author: Author,
    val content: String
) : Parcelable {
    companion object {
        fun fromResponse(reviewDTO: ReviewDTO): Review = Review(
            author = Author.fromResponse(reviewDTO.author),
            content = reviewDTO.content
        )
    }
}
