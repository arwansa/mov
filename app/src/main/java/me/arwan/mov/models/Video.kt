package me.arwan.mov.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import me.arwan.mov.models.response.trailer.VideoDTO

@Parcelize
data class Video(
    val name: String,
    val key: String,
) : Parcelable {
    companion object {
        fun fromResponse(videoDTO: VideoDTO): Video = Video(
            name = videoDTO.name,
            key = videoDTO.key
        )
    }
}