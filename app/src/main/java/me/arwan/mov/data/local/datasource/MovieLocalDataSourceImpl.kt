package me.arwan.mov.data.local.datasource

import kotlinx.coroutines.flow.Flow
import me.arwan.mov.data.local.database.GenreDAO
import me.arwan.mov.models.Genre

class MovieLocalDataSourceImpl(
    private val genreDAO: GenreDAO,
) : MovieLocalDataSource {

    override val listGenres: Flow<List<Genre>> = genreDAO.getGenres()

    override suspend fun upsertGenres(genres: List<Genre>) {
        genreDAO.upsertGenres(genres)
    }
}