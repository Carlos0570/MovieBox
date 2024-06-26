package com.example.moviebox.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.moviebox.R

@Composable
fun MediumCard(imageURL: String, text: String? = null, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.BottomStart
    ) {
        AsyncImage(
            model = "${stringResource(id = R.string.image_url_500)}$imageURL",
            placeholder = null,
            error = null,
            contentDescription = "Image",
            modifier = Modifier
                .height(340.dp)
                .width(170.dp)
                .clickable { onClick.invoke() },
            contentScale = ContentScale.Crop
        )
        if (text.isNullOrBlank().not())
            Box(
                modifier = Modifier
                    .width(170.dp)
                    .background(MaterialTheme.colorScheme.tertiary)
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    text = text ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1
                )
            }

    }
}