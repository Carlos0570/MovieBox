package com.example.moviebox.youtubePlayer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YoutubeScreen(videoId: String, onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        GradientBox(modifier = Modifier.fillMaxSize())
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            YoutubePlayer(videoId = videoId)
            CloseButton { onDismiss() }
        }
    }
}

@Composable
private fun YoutubePlayer(videoId: String) {
    Box(
        Modifier
            .padding(6.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(MaterialTheme.colorScheme.tertiary)
    ) {
        AndroidView(
            factory = { context ->
                YouTubePlayerView(context).apply {
                    addYouTubePlayerListener(
                        object : AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                super.onReady(youTubePlayer)
                                youTubePlayer.loadVideo(videoId, 0f)
                            }
                        }
                    )
                }
            }
        )
    }
}

@Composable
private fun CloseButton(onDismiss: () -> Unit) {
    IconButton(
        onClick = { onDismiss() }, modifier = Modifier
            .background(Color.Transparent)
            .padding(30.dp)
    ) {
        Icon(Icons.Default.Close, contentDescription = null)
    }
}

@Composable
private fun GradientBox(modifier: Modifier) {
    val gradient = Brush.radialGradient(
        0.0f to MaterialTheme.colorScheme.background,
        1.0f to Color.Transparent,
        radius = 2300.0f
    )
    Box(modifier = modifier.background(gradient))
}


