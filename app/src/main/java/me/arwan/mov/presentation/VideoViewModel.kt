package me.arwan.mov.presentation

import androidx.lifecycle.ViewModel
import me.arwan.mov.core.utils.ExceptionManager
import me.arwan.mov.core.utils.Resource
import me.arwan.mov.core.utils.launchSafeIO
import me.arwan.mov.domain.MoviesRepository
import me.arwan.mov.models.Video
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val moviesRepo: MoviesRepository,
) : ViewModel() {

    private val _messageVideo = Channel<Int>()
    val messageVideo = _messageVideo.receiveAsFlow()

    private var jobRequestVideo: Job? = null

    private val _listVideoMovie = MutableStateFlow<Resource<List<Video>>>(Resource.Loading)
    val listVideoMovie = _listVideoMovie.asStateFlow()

    private var id: Long = -1

    fun getVideoFromMovie(movieId: Long) {
        jobRequestVideo?.cancel()
        if (id != movieId || _listVideoMovie.value is Resource.Failure) {
            id = movieId
            jobRequestVideo = launchSafeIO(
                blockBefore = { _listVideoMovie.value = Resource.Loading },
                blockIO = {
                    val listVideo = moviesRepo.getVideos(movieId)
                    withContext(Dispatchers.Main) {
                        _listVideoMovie.value = Resource.Success(listVideo)
                    }
                },
                blockException = { exception ->
                    _listVideoMovie.value = Resource.Failure
                    _messageVideo.trySend(
                        ExceptionManager.getMessageForException(
                            exception,
                            "Error request video from $movieId:"
                        )
                    )
                }
            )
        }
    }
}