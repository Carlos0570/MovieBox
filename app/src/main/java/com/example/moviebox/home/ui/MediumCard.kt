package com.example.moviebox.home.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.moviebox.R

@Composable
fun MediumCard(imageURL: String,onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        AsyncImage(
            model = "${stringResource(id = R.string.image_url_500)}$imageURL",
            placeholder = null,
            error = null,
            contentDescription = "Image",
            modifier = Modifier
                .height(340.dp)
                .width(170.dp)
                .clickable {onClick.invoke()},
            contentScale = ContentScale.Crop
        )
    }
}