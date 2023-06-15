package me.arwan.mov.inject

import android.content.Context
import androidx.room.Room
import me.arwan.mov.data.local.database.MovieDataBase
import me.arwan.mov.data.local.datasource.MovieLocalDataSource
import me.arwan.mov.data.local.datasource.MovieLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.arwan.mov.data.local.database.GenreDAO
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideMoviesDatabase(
        @ApplicationContext context: Context,
    ): MovieDataBase = Room.databaseBuilder(
        context,
        MovieDataBase::class.java,
        "MOVIES_DB"
    ).build()

    @Provides
    @Singleton
    fun provideGenreDao(
        movieDataBase: MovieDataBase,
    ): GenreDAO = movieDataBase.getGenresDao()

    @Singleton
    @Provides
    fun provideMoviesDataSource(
        genreDao: GenreDAO,
    ): MovieLocalDataSource = MovieLocalDataSourceImpl(genreDao)

}