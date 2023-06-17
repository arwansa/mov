package me.arwan.mov.ui.screens.review

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import me.arwan.mov.core.utils.shareViewModel
import me.arwan.mov.models.Movie
import me.arwan.mov.presentation.ReviewViewModel
import me.arwan.mov.ui.screens.review.component.ItemReview
import me.arwan.mov.ui.share.LoadingCircular
import me.arwan.mov.ui.share.RetryButton
import me.arwan.mov.ui.share.ToolbarBack
import me.arwan.mov.ui.states.ReviewScreenState
import me.arwan.mov.ui.states.rememberReviewScreenState

@Destination
@Composable
fun ReviewScreen(
    movie: Movie,
    navigator: DestinationsNavigator,
    reviewViewModel: ReviewViewModel = shareViewModel(),
    reviewScreenState: ReviewScreenState = rememberReviewScreenState()
) {

    val reviews = reviewViewModel.getAllMovieReview(movie.id).collectAsLazyPagingItems()

    Scaffold(
        scaffoldState = reviewScreenState.scaffoldState,
        topBar = {
            ToolbarBack(
                title = "Reviews: ${movie.title}",
                actionBack = { navigator.popBackStack() }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            items(reviews.itemCount) { index ->
                reviews[index]?.let { review ->
                    ItemReview(review)
                }
            }
            reviews.apply {
                item {
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
}