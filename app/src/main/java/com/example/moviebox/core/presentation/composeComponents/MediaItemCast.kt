package com.example.moviebox.core.presentation.composeComponents

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moviebox.core.data.dataClasses.Cast
import com.example.moviebox.core.navigation.Screen

@Composable
fun MediaItemCast(cast: List<Cast>, navController: NavController) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(3),
        modifier = Modifier.padding(9.dp)
    ) {
        items(cast) { cast ->
            if (cast.profilePath.isNullOrBlank().not())
                ActorCard(cast = cast) {
                    navController.navigate(
                        Screen.CastDetail.createRoute(castId = cast.id ?: 0)
                    )
                }
        }
    }
}