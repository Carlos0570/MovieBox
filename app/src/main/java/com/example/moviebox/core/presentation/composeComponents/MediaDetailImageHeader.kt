package com.example.moviebox.core.presentation.composeComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.moviebox.R
import com.example.moviebox.core.data.dataClasses.MediaItem

@Composable
fun MediaItemImageHeader(mediaItem: MediaItem?, mediaName: String) {
    var sizeImage by remember { mutableStateOf(IntSize.Zero) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { sizeImage = it.size }
    ) {
        MediaBackgroundImage(mediaItem)
        GradientBox(sizeImage, Modifier.matchParentSize())
        MediaTitle(
            mediaName = mediaName,
            score = mediaItem?.voteAverage,
            modifier = Modifier
                .padding(9.dp)
                .align(Alignment.BottomStart)
                .fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun MediaBackgroundImage(serieDetail: MediaItem?) {
    serieDetail?.let {
        GlideImage(
            model = "${stringResource(R.string.image_url_500)}${serieDetail.backdropPath ?: serieDetail.posterPath}",
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
        )
    }
}

@Composable
private fun GradientBox(sizeImage: IntSize, modifier: Modifier) {
    val gradient = Brush
        .verticalGradient(
            colors = listOf(
                Color.Transparent,
                MaterialTheme.colorScheme.background
            ),
            startY = sizeImage.height.toFloat() / 2,
            endY = sizeImage.height.toFloat()
        )
    Box(modifier = modifier.background(gradient))
}

@Composable
private fun MediaTitle(mediaName: String, score: Double?, modifier: Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = mediaName,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .heightIn(0.dp, 232.dp)
                .widthIn(0.dp, 270.dp)
        )
        UserScore(score)
    }
}

@Composable
private fun UserScore(score: Double?) {
    score?.let {
        if (it > 0)
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = stringResource(R.string.user_score),
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.titleSmall,
                    lineHeight = 18.sp
                )
                Text(
                    text = String.format("%.1f", it) + "%",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.End
                )
            }
    }
}
