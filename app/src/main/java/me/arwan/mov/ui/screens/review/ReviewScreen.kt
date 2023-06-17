package me.arwan.mov.ui.screens.review

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import me.arwan.mov.core.utils.Resource
import me.arwan.mov.core.utils.shareViewModel
import me.arwan.mov.models.Movie
import me.arwan.mov.models.Review
import me.arwan.mov.presentation.ReviewViewModel
import me.arwan.mov.ui.screens.review.component.ReviewItem
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

    val stateReviews by reviewViewModel.listReview.collectAsState()

    reviewViewModel.getMovieReview(movie.id, 1)

    LaunchedEffect(key1 = Unit) {
        reviewViewModel.messageReview.collect(reviewScreenState::showSnackMessage)
    }

    Scaffold(
        scaffoldState = reviewScreenState.scaffoldState,
        topBar = {
            ToolbarBack(
                title = "Reviews: ${movie.title}",
                actionBack = { navigator.popBackStack() }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
        ) {
            ListReviewState(stateReviews) {
                reviewViewModel.getMovieReview(movie.id, 1)
            }
        }
    }
}

@Composable
private fun ListReviewState(reviews: Resource<List<Review>>, retryClick: () -> Unit) {
    when (reviews) {
        is Resource.Success -> {
            LazyColumn {
                items(reviews.data, key = { it.id }) { review ->
                    ReviewItem(review)
                }
            }
        }

        is Resource.Loading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        else -> RetryButton {
            retryClick()
        }
    }
}