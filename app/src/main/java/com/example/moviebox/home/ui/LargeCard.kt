package com.example.moviebox.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.moviebox.core.navigation.Screen
import jp.wasabeef.glide.transformations.BlurTransformation
import com.example.moviebox.R
import com.example.moviebox.core.data.dataClasses.Movie

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LargeCard(homeViewModel: HomeViewModel, navController: NavController) {
    val recommendedMovie by homeViewModel.recommendedMovie.collectAsState()
    if (recommendedMovie != null)
        Box {
            Background(recommendedMovie, Modifier.matchParentSize())
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .shadow(10.dp)
                    .align(Alignment.Center)
                    .clickable {
                        navController.navigate(
                            Screen.MovieDetailScreen.createRoute(recommendedMovie?.id ?: 0)
                        )
                    }
            ) {
                GlideImage(
                    model = "${stringResource(id = R.string.image_url_500)}${recommendedMovie?.posterPath}",
                    contentDescription = "",
                    modifier = Modifier
                        .height(380.dp)
                        .width(280.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun Background(recommendedMovie: Movie?, modifier: Modifier) {
    GlideImage(
        model = "${stringResource(id = R.string.image_url_500)}${recommendedMovie?.posterPath}",
        contentDescription = "",
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        contentScale = ContentScale.Crop
    ) {
        // it.diskCacheStrategy(DiskCacheStrategy.ALL)
        it.transform(BlurTransformation(25))
    }
    val gradient = Brush
        .verticalGradient(
            colors = listOf(
                Color.Transparent,
                MaterialTheme.colorScheme.background
            )
        )
    Box(modifier = modifier.background(gradient))
}

