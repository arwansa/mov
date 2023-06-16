package me.arwan.mov.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.arwan.mov.models.Genre

@Database(
    entities = [Genre::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDataBase : RoomDatabase() {
    abstract fun getGenresDao(): GenreDAO
}