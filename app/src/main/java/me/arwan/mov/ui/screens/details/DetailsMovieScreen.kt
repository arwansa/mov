package me.arwan.mov.ui.screens.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.arwan.mov.R
import me.arwan.mov.core.utils.Resource
import me.arwan.mov.core.utils.shareViewModel
import me.arwan.mov.models.Cast
import me.arwan.mov.models.Movie
import me.arwan.mov.presentation.CastViewModel
import me.arwan.mov.ui.screens.details.componets.HeaderMovie
import me.arwan.mov.ui.screens.details.componets.ItemCast
import me.arwan.mov.ui.share.FakeListScroll
import me.arwan.mov.ui.states.DetailsScreenState
import me.arwan.mov.ui.states.rememberDetailsScreenState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import me.arwan.mov.models.Video
import me.arwan.mov.presentation.VideoViewModel
import me.arwan.mov.ui.screens.destinations.ReviewScreenDestination
import me.arwan.mov.ui.screens.details.componets.YouTubeVideoPreview
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy

@Destination
@Composable
fun DetailsMovieScreen(
    movie: Movie,
    navigator: DestinationsNavigator,
    castViewModel: CastViewModel = shareViewModel(),
    videoViewModel: VideoViewModel = shareViewModel(),
    detailsStateScreen: DetailsScreenState = rememberDetailsScreenState()
) {

    val stateListCast by castViewModel.listCastMovie.collectAsState()
    val stateListVideo by videoViewModel.listVideoMovie.collectAsState()

    val maxTextLinesTitle by remember(detailsStateScreen.progressCollapsing) {
        derivedStateOf {
            if (detailsStateScreen.progressCollapsing < 0.8f) 1 else Int.MAX_VALUE
        }
    }

    LaunchedEffect(key1 = Unit) {
        castViewModel.messageCast.collect(detailsStateScreen::showSnackMessage)
        videoViewModel.messageVideo.collect(detailsStateScreen::showSnackMessage)
    }

    Scaffold(
        scaffoldState = detailsStateScreen.scaffoldState
    ) {
        CollapsingToolbarScaffold(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
            state = detailsStateScreen.toolbarState,
            scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
            toolbar = {
                HeaderMovie(
                    movie = movie,
                    modifier = Modifier.parallax(),
                    paddingHeader = detailsStateScreen.innerPadding,
                    alphaBackground = detailsStateScreen.progressCollapsing
                )

                IconBack(
                    actionClickBack = navigator::popBackStack,
                    modifier = Modifier.size(detailsStateScreen.paddingIconNav.dp)
                )

                TitleMovieRoad(
                    title = movie.title,
                    maxLinesTitle = maxTextLinesTitle,
                    fontSize = detailsStateScreen.textSizeTitle,
                    heightMinToolbar = detailsStateScreen.heightMinToolbar,
                    modifier = Modifier
                        .road(Alignment.TopStart, Alignment.TopCenter)
                        .padding(
                            start = detailsStateScreen.paddingStartTitle.dp,
                            end = detailsStateScreen.innerPadding.dp,
                            top = detailsStateScreen.paddingTopTitle.dp,
                        )
                )
            }
        ) {

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                OverviewMovie(movie = movie)
                ListVideoState(listVideo = stateListVideo)
                Spacer(modifier = Modifier.height(10.dp))
                ListCastState(listCast = stateListCast)
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        navigator.navigate(ReviewScreenDestination(movie))
                    },
                    modifier = Modifier
                        .wrapContentWidth()
                        .align(Alignment.CenterHorizontally),
                    shape = CircleShape
                ) {
                    Text(text = stringResource(R.string.title_show_reviews))
                }
                Spacer(modifier = Modifier.height(15.dp))
            }

        }
    }
}

@Composable
private fun OverviewMovie(movie: Movie) {
    Card(modifier = Modifier.padding(10.dp), shape = RoundedCornerShape(5.dp)) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = stringResource(R.string.title_overview),
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = movie.description, style = MaterialTheme.typography.body1)
        }
    }
}

@Composable
private fun ListVideoState(
    listVideo: Resource<List<Video>>
) {
    when (listVideo) {
        is Resource.Success -> {
            Text(
                text = stringResource(R.string.title_video),
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
                style = MaterialTheme.typography.h6
            )
            LazyRow {
                items(listVideo.data,
                    key = { it.name }) { video ->
                    YouTubeVideoPreview(video.source)
                }
            }
        }

        else -> FakeListScroll()
    }
}

@Composable
private fun ListCastState(
    listCast: Resource<List<Cast>>
) {
    when (listCast) {
        is Resource.Success -> {
            Text(
                text = stringResource(R.string.title_casting),
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
                style = MaterialTheme.typography.h6
            )
            LazyRow {
                items(listCast.data,
                    key = { it.name }) { cast ->
                    ItemCast(cast)
                }
            }
        }

        else -> FakeListScroll()
    }
}

@Composable
private fun TitleMovieRoad(
    title: String,
    maxLinesTitle: Int,
    fontSize: Float,
    heightMinToolbar: Float,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(heightMinToolbar.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            modifier = Modifier,
            color = Color.White,
            fontSize = fontSize.sp,
            maxLines = maxLinesTitle,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun IconBack(
    actionClickBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = actionClickBack,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = stringResource(id = R.string.description_arrow_back),
            tint = Color.White,
        )
    }
}