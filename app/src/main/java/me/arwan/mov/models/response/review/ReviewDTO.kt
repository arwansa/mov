package me.arwan.mov.models.response.review

import com.google.gson.annotations.SerializedName

data class ReviewDTO(
    val id: String,
    @SerializedName("author_details")
    val author: AuthorDTO,
    val content: String
)