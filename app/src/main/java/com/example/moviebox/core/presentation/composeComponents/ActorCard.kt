package com.example.moviebox.core.presentation.composeComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.moviebox.core.data.dataClasses.Cast
import com.example.moviebox.R

@Composable
fun ActorCard(cast: Cast, onClick: () -> Unit) {
    Box(
        Modifier
            .size(width = 160.dp, height = 180.dp)
            .padding(4.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(MaterialTheme.colorScheme.tertiary)
            .clickable {
                onClick.invoke()
            }
    ) {
        Column {
            AsyncImage(
                model = "${stringResource(id = R.string.image_url_200)}${cast.profilePath}",
                placeholder = null,
                error = null,
                contentDescription = "",
                modifier = Modifier.height(130.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = cast.character ?: "",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.width(110.dp).padding(start = 2.dp),
                maxLines = 1,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = cast.name ?: "",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.width(110.dp).padding(start = 2.dp),
                maxLines = 1
            )
        }
    }
}