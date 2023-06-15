package me.arwan.mov.presentation

import androidx.lifecycle.ViewModel
import me.arwan.mov.core.utils.ExceptionManager
import me.arwan.mov.core.utils.Resource
import me.arwan.mov.core.utils.launchSafeIO
import me.arwan.mov.domain.MoviesRepository
import me.arwan.mov.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepo: MoviesRepository,
) : ViewModel() {

    private val _messageMovie = Channel<Int>()
    val messageMovie = _messageMovie.receiveAsFlow()

    private var jobRequestMovie: Job? = null

    private val _listMovie = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading)
    val listMovie = _listMovie.asStateFlow()

    private var genreId: Long = -1
    private var currentPage: Int = -1

    fun getMovieByGenre(withGenre: Long, page: Int) {
        jobRequestMovie?.cancel()
        if (genreId != withGenre || currentPage != page || _listMovie.value is Resource.Failure) {
            genreId = withGenre
            currentPage = page
            jobRequestMovie = launchSafeIO(
                blockBefore = { _listMovie.value = Resource.Loading },
                blockIO = {
                    val listMovie = moviesRepo.getDiscoverMoviesByGenre(withGenre, page)
                    withContext(Dispatchers.Main) {
                        _listMovie.value = Resource.Success(listMovie)
                    }
                },
                blockException = { exception ->
                    _listMovie.value = Resource.Failure
                    _messageMovie.trySend(
                        ExceptionManager.getMessageForException(
                            exception,
                            "Error request movies from $withGenre page $page:"
                        )
                    )
                }
            )
        }
    }
}