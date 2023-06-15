package me.arwan.mov.ui.screens.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import me.arwan.mov.core.utils.shareViewModel
import me.arwan.mov.presentation.CastViewModel
import me.arwan.mov.presentation.MoviesViewModel
import me.arwan.mov.ui.share.ToolbarBack
import me.arwan.mov.ui.states.MoviesScreenState
import me.arwan.mov.ui.states.rememberMoviesScreenState
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import me.arwan.mov.core.utils.Resource
import me.arwan.mov.models.Genre
import me.arwan.mov.models.Movie
import me.arwan.mov.ui.screens.destinations.DetailsMovieScreenDestination
import me.arwan.mov.ui.screens.movies.components.FakeListScroll
import me.arwan.mov.ui.screens.movies.components.ItemMovie

@Destination
@Composable
fun MoviesScreen(
    genre: Genre,
    navigator: DestinationsNavigator,
    castViewModel: CastViewModel = shareViewModel(),
    moviesViewModel: MoviesViewModel = shareViewModel(),
    moviesScreenState: MoviesScreenState = rememberMoviesScreenState()
) {
    val stateMovies by moviesViewModel.listMovie.collectAsState()

    moviesViewModel.getMovieByGenre(genre.id, 1)

    LaunchedEffect(key1 = Unit) {
        moviesViewModel.messageMovie.collect(moviesScreenState::showSnackMessage)
    }

    Scaffold(
        scaffoldState = moviesScreenState.scaffoldState,
        topBar = {
            ToolbarBack(
                title = genre.name,
                actionBack = { navigator.popBackStack() }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            Movies(movies = stateMovies, actionClick = { movie ->
                castViewModel.getCastFromMovie(movie.id)
                navigator.navigate(DetailsMovieScreenDestination(movie))
            })
        }
    }
}

@Composable
fun Movies(movies: Resource<List<Movie>>, actionClick: (Movie) -> Unit) {
    when (movies) {
        is Resource.Success -> {
            movies.data.forEach {
                ItemMovie(movie = it, actionClick = actionClick)
            }
        }

        else -> FakeListScroll()
    }
}

