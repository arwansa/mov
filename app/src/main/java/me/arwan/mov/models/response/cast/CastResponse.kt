package me.arwan.mov.models.response.cast

data class CastResponse(
    val cast: List<CastDTO>,
    val id: Long
)