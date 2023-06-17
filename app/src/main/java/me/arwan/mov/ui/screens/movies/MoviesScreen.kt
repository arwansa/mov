package me.arwan.mov.ui.screens.movies

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.ramcosta.composedestinations.annotation.Destination
import me.arwan.mov.core.utils.shareViewModel
import me.arwan.mov.presentation.CastViewModel
import me.arwan.mov.presentation.MoviesViewModel
import me.arwan.mov.ui.share.ToolbarBack
import me.arwan.mov.ui.states.MoviesScreenState
import me.arwan.mov.ui.states.rememberMoviesScreenState
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import me.arwan.mov.models.Genre
import me.arwan.mov.presentation.VideoViewModel
import me.arwan.mov.ui.screens.destinations.DetailsMovieScreenDestination
import me.arwan.mov.ui.screens.movies.components.MovieGridView

@Destination
@Composable
fun MoviesScreen(
    genre: Genre,
    navigator: DestinationsNavigator,
    videoViewModel: VideoViewModel = shareViewModel(),
    castViewModel: CastViewModel = shareViewModel(),
    moviesViewModel: MoviesViewModel = shareViewModel(),
    moviesScreenState: MoviesScreenState = rememberMoviesScreenState()
) {

    val movies = moviesViewModel.getAllMovieByGenre(genre.id).collectAsLazyPagingItems()

    Scaffold(
        scaffoldState = moviesScreenState.scaffoldState,
        topBar = {
            ToolbarBack(
                title = genre.name,
                actionBack = { navigator.popBackStack() }
            )
        }
    ) {
        MovieGridView(
            modifier = Modifier.padding(it),
            movieList = movies
        ) { movie ->
            videoViewModel.getVideoFromMovie(movie.id)
            castViewModel.getCastFromMovie(movie.id)
            navigator.navigate(DetailsMovieScreenDestination(movie))
        }
    }
}

