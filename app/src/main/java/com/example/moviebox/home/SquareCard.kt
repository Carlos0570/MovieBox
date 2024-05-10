package com.example.moviebox.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.moviebox.R

@Composable
fun SquareCard(imageURL: String, name: String?, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(7.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(MaterialTheme.colorScheme.tertiary)
            .clickable { onClick.invoke() }
    ) {
        Column {
            AsyncImage(
                model = "${stringResource(id = R.string.image_url_500)}$imageURL",
                placeholder = null,
                error = null,
                contentDescription = "Image",
                modifier = Modifier
                    .height(170.dp)
                    .width(230.dp),
                contentScale = ContentScale.Crop
            )
            name?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .width(230.dp)
                        .padding(start = 2.dp),
                    maxLines = 1,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}