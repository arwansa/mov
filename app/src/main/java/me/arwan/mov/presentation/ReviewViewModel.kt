package me.arwan.mov.presentation

import androidx.lifecycle.ViewModel
import me.arwan.mov.core.utils.ExceptionManager
import me.arwan.mov.core.utils.Resource
import me.arwan.mov.core.utils.launchSafeIO
import me.arwan.mov.domain.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import me.arwan.mov.models.Review
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val moviesRepo: MoviesRepository,
) : ViewModel() {

    private val _messageReview = Channel<Int>()
    val messageReview = _messageReview.receiveAsFlow()

    private var jobRequestMovie: Job? = null

    private val _listReview = MutableStateFlow<Resource<List<Review>>>(Resource.Loading)
    val listReview = _listReview.asStateFlow()

    private var id: Long = -1
    private var currentPage: Int = -1

    fun getMovieReview(movieId: Long, page: Int) {
        jobRequestMovie?.cancel()
        if (id != movieId || currentPage != page || _listReview.value is Resource.Failure) {
            id = movieId
            currentPage = page
            jobRequestMovie = launchSafeIO(
                blockBefore = { _listReview.value = Resource.Loading },
                blockIO = {
                    val listMovie = moviesRepo.getMovieReviews(movieId, page)
                    withContext(Dispatchers.Main) {
                        _listReview.value = Resource.Success(listMovie)
                    }
                },
                blockException = { exception ->
                    _listReview.value = Resource.Failure
                    _messageReview.trySend(
                        ExceptionManager.getMessageForException(
                            exception,
                            "Error request reviews from $movieId page $page:"
                        )
                    )
                }
            )
        }
    }
}