package me.arwan.mov.ui.screens.genre

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
import androidx.compose.ui.res.stringResource
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import me.arwan.mov.R
import me.arwan.mov.core.utils.shareViewModel
import me.arwan.mov.presentation.GenresViewModel
import me.arwan.mov.ui.screens.destinations.MoviesScreenDestination
import me.arwan.mov.ui.screens.destinations.SearchScreenDestination
import me.arwan.mov.ui.screens.genre.components.ListGenre
import me.arwan.mov.ui.share.ToolbarBack
import me.arwan.mov.ui.states.GenresScreenState
import me.arwan.mov.ui.states.rememberGenresScreenState

@Destination(start = true)
@Composable
fun GenresScreen(
    navigator: DestinationsNavigator,
    genresViewModel: GenresViewModel = shareViewModel(),
    genresScreenState: GenresScreenState = rememberGenresScreenState()
) {
    val stateGenres by genresViewModel.listGenres.collectAsState()
    LaunchedEffect(key1 = Unit) {
        genresViewModel.messageGenre.collect(genresScreenState::showSnackMessage)
    }

    Scaffold(
        scaffoldState = genresScreenState.scaffoldState,
        topBar = {
            ToolbarBack(
                title = stringResource(id = R.string.app_name),
                actionSearch = { navigator.navigate(SearchScreenDestination) }
            )
        }
    ) {
        SwipeRefresh(
            onRefresh = genresViewModel::getGenres,
            state = rememberSwipeRefreshState(genresViewModel.isRequested),
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .verticalScroll(rememberScrollState())
            ) {
                ListGenre(
                    genres = stateGenres,
                    actionClick = {
                        navigator.navigate(MoviesScreenDestination(it))
                    },
                    retryClick = { genresViewModel.getGenres() })
            }
        }
    }
}