package me.arwan.mov.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import me.arwan.mov.core.utils.ExceptionManager
import me.arwan.mov.core.utils.Resource
import me.arwan.mov.core.utils.launchSafeIO
import me.arwan.mov.domain.MoviesRepository
import me.arwan.mov.models.Genre
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GenresViewModel @Inject constructor(
    private val moviesRepo: MoviesRepository,
) : ViewModel() {

    private val _messageGenres = Channel<Int>()
    val messageGenre = _messageGenres.receiveAsFlow()

    val listGenres = flow<Resource<List<Genre>>> {
        moviesRepo.listGenres.collect {
            emit(Resource.Success(it))
        }
    }.catch {
        Timber.e("Error loading genres $it")
        emit(Resource.Failure)
    }.flowOn(Dispatchers.IO).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        Resource.Loading
    )

    private var jobRequest: Job? = null
    var isRequested by mutableStateOf(true)

    init {
        getGenres()
    }

    fun getGenres() {
        jobRequest?.cancel()

        jobRequest = launchSafeIO(
            blockBefore = { isRequested = true },
            blockAfter = { isRequested = false },
            blockIO = {
                val sizeGenre = async(Dispatchers.IO) { moviesRepo.upsertGenres() }
                val size= sizeGenre.await()
                Timber.d("Size genre $size")
            },
            blockException = {
                _messageGenres.trySend(
                    ExceptionManager.getMessageForException(it, "Error request genres")
                )
            }
        )
    }

}