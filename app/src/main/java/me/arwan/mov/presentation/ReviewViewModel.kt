package me.arwan.mov.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import me.arwan.mov.domain.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import me.arwan.mov.models.Review
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val moviesRepo: MoviesRepository,
) : ViewModel() {

    fun getAllMovieReview(movieId: Long): Flow<PagingData<Review>> =
        moviesRepo.getAllMovieReviews(movieId).cachedIn(viewModelScope)
}