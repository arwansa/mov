package me.arwan.mov.ui.screens.genre.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.arwan.mov.core.utils.Resource
import me.arwan.mov.models.Genre
import me.arwan.mov.ui.share.RetryButton

@Composable
fun ListGenre(
    genres: Resource<List<Genre>>,
    actionClick: (Genre) -> Unit,
    retryClick: () -> Unit
) {
    when (genres) {
        is Resource.Success -> {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Movie Genres",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )
                genres.data.forEach { genre ->
                    Button(
                        onClick = { actionClick(genre) },
                        modifier = Modifier
                            .wrapContentWidth()
                            .align(Alignment.CenterHorizontally),
                        shape = CircleShape
                    ) {
                        Text(text = genre.name)
                    }
                }
            }
        }

        is Resource.Failure -> RetryButton {
            retryClick()
        }

        else -> {}
    }
}