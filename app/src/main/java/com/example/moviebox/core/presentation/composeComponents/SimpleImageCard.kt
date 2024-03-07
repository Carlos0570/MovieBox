package com.example.moviebox.core.presentation.composeComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.moviebox.R


@Composable
fun SimpleImageCard(posterPath : String?, onClick: () -> Unit) {
    posterPath?.let {
        Box(
            Modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(MaterialTheme.colorScheme.tertiary)
                .clickable {
                    onClick.invoke()
                }
        ) {
            Column {
                AsyncImage(
                    model = "${stringResource(id = R.string.image_url_200)}${posterPath}",
                    placeholder = null,
                    error = null,
                    contentDescription = "Image",
                    modifier = Modifier
                        .height(230.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }

}