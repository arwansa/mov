package me.arwan.mov.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import me.arwan.mov.models.response.trailer.VideoDTO

@Parcelize
data class Video(
    val name: String,
    val key: String,
    val source: String
) : Parcelable {
    companion object {
        fun fromResponse(videoDTO: VideoDTO): Video = Video(
            name = videoDTO.name,
            key = videoDTO.key,
            source = """
                <div>
                  <div style="position:relative;padding-top:56.25%;">
                    <iframe src="https://www.youtube.com/embed/${videoDTO.key}" frameborder="0" allowfullscreen
                      style="position:absolute;top:0;left:0;width:100%;height:100%;"></iframe>
                  </div>
                </div>
            """
        )
    }
}