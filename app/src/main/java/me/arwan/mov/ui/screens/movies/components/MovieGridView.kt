package me.arwan.mov.ui.screens.movies.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import me.arwan.mov.models.Movie
import me.arwan.mov.ui.share.LoadingCircular
import me.arwan.mov.ui.share.RetryButton

@Composable
fun MovieGridView(
    modifier: Modifier,
    movieList: LazyPagingItems<Movie>,
    actionClick: (Movie) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = rememberLazyGridState(),
    ) {
        items(movieList.itemCount) { index ->
            movieList[index]?.let { movie ->
                ItemMovie(movie = movie, actionClick = actionClick)
            }
        }
        movieList.apply {
            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                when {
                    loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
                        LoadingCircular()
                    }

                    loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
                        RetryButton {
                            retry()
                        }
                    }
                }
            }
        }
    }
}