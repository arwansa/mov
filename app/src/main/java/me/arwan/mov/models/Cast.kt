package me.arwan.mov.models

import android.os.Parcelable
import me.arwan.mov.models.response.cast.CastDTO
import kotlinx.parcelize.Parcelize
import me.arwan.mov.BuildConfig

@Parcelize
data class Cast(
    val name: String,
    val urlImg: String? = null,
) : Parcelable {
    companion object {
        fun fromResponse(castDTO: CastDTO): Cast {
            val urlImg = castDTO.profile_path?.let {
                "${BuildConfig.PREFIX_IMAGE_URL}$it"
            }
            return Cast(
                name = castDTO.name,
                urlImg = urlImg
            )
        }
    }
}