package me.arwan.mov.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import me.arwan.mov.models.response.genre.GenreDTO

@Parcelize
@Entity(tableName = "table_genres")
data class Genre(
    @PrimaryKey
    val id: Long,
    val name: String,
) : Parcelable {
    companion object {
        fun fromResponse(genreDTO: GenreDTO): Genre = Genre(
            id = genreDTO.id,
            name = genreDTO.name
        )
    }
}