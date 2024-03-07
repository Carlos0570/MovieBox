package com.example.moviebox.core.presentation.composeComponents


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moviebox.core.data.dataClasses.MediaItem

@Composable
fun SimilarMediaItems(
    similarMediaItem: List<MediaItem>,
    onClick: (Int) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier
            .padding(9.dp)
    ) {
        items(similarMediaItem) { mediaItem ->
            if (mediaItem.posterPath.isNullOrBlank().not())
                SimpleImageCard(
                    mediaItem.posterPath
                ) {
                    onClick(mediaItem.id ?: 0)
                }
        }
    }
}