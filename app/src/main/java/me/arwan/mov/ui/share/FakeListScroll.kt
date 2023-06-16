package me.arwan.mov.ui.share

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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
        TitleFakeMovie(modifier = modifier.myShimmer(shimmer))
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(
                count = 5,
                key = { it }
            ) {
                FakeCardContainer(
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