package me.arwan.mov.data.local.database

import android.database.sqlite.SQLiteConstraintException
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import me.arwan.mov.models.Genre

@Dao
interface GenreDAO {
    @Query("SELECT * FROM table_genres")
    fun getGenres(): Flow<List<Genre>>

    @Insert
    suspend fun insertGenre(genre: Genre)

    @Update()
    suspend fun updateGenre(genre: Genre)

    @Transaction
    suspend fun upsertGenres(genres: List<Genre>) {
        genres.forEach {
            try {
                insertGenre(it)
            } catch (e: SQLiteConstraintException) {
                updateGenre(it)
            }
        }
    }
}