package me.arwan.mov.ui.screens.movies.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import me.arwan.mov.core.utils.myShimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
fun FakeListScroll(
    modifier: Modifier = Modifier
) {
    val shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.View)

    Column(modifier = modifier) {
        TitleFakeMovie(
            modifier = modifier.myShimmer(shimmer)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                count = 12,
                key = { it }
            ) {
                CardContainerFake(
                    modifier = Modifier.myShimmer(shimmer)
                )
            }
        }
    }
}

@Composable
private fun TitleFakeMovie(
    modifier: Modifier = Modifier
) {

    Box(
        modifier = Modifier
            .padding(vertical = 5.dp, horizontal = 10.dp)
            .size(width = 120.dp, height = 20.dp)
            .clip(RoundedCornerShape(5.dp))
            .then(modifier)
    )
}

@Composable
private fun CardContainerFake(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .width(150.dp)
            .padding(4.dp)
            .clip(RoundedCornerShape(10.dp))
            .then(modifier)

    )
}