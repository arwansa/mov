package me.arwan.mov.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.arwan.mov.models.Genre
import me.arwan.mov.models.Movie

@Database(
    entities = [Genre::class, Movie::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDataBase : RoomDatabase() {
    abstract fun getGenresDao(): GenreDAO
}