package me.arwan.mov.models.response.review

import com.google.gson.annotations.SerializedName

data class AuthorDTO(
    val username: String,
    @SerializedName("avatar_path")
    val avatarPath: String?,
    val rating: Double
)
