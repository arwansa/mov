package me.arwan.mov.ui.share

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun FakeCardContainer(
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