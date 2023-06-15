package me.arwan.mov.models.response.review

data class ReviewResponse(
    val page: Int,
    val results: List<ReviewDTO>,
    val total_pages: Int,
    val total_results: Int
)