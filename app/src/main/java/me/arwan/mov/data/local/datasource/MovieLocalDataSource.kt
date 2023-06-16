package me.arwan.mov.data.local.datasource

import kotlinx.coroutines.flow.Flow
import me.arwan.mov.models.Genre

interface MovieLocalDataSource {
    val listGenres: Flow<List<Genre>>

    suspend fun upsertGenres(genres: List<Genre>)
}