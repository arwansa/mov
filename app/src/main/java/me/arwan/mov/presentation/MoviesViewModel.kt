package me.arwan.mov.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import me.arwan.mov.domain.MoviesRepository
import me.arwan.mov.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepo: MoviesRepository,
) : ViewModel() {

    fun getAllMovieByGenre(withGenre: Long): Flow<PagingData<Movie>> =
        moviesRepo.getAllDiscoverMoviesByGenre(withGenre).cachedIn(viewModelScope)

}