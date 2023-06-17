package me.arwan.mov.ui.screens.review.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.arwan.mov.R
import me.arwan.mov.models.Review
import me.arwan.mov.ui.share.AsyncImageFade
import me.arwan.mov.ui.theme.Orange200
import me.arwan.mov.ui.theme.Orange700

@Composable
fun ItemReview(review: Review) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            AsyncImageFade(
                data = review.author.avatarPath,
                modifier = Modifier.size(48.dp),
                contentDescription = R.string.description_img_actor,
                resourceLoading = R.drawable.ic_person,
                resourceFailed = R.drawable.ic_error_person,
            )
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)

            ) {
                Text(text = review.author.username, color = Orange700)
                Text(
                    text = review.author.rating.toString(),
                    color = Orange200
                )
                Text(
                    text = review.content,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}