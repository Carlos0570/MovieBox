package com.example.moviebox.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.moviebox.R
import com.example.moviebox.core.navigation.Screen
import com.example.moviebox.core.presentation.composeComponents.LoadingAnimation
import com.example.moviebox.core.presentation.screenStates.ScreenError
import com.example.moviebox.core.presentation.screenStates.ScreenState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HomeScreenBody(
    homeViewModel: HomeViewModel,
    navController: NavController
) {
    val state by homeViewModel.state.collectAsState()
    when (state) {
        ScreenState.LOADING -> LoadingAnimation()
        ScreenState.SUCCESS -> HomeScreen(homeViewModel, navController)
        is ScreenState.FAILURE -> ScreenError { homeViewModel.initHomeScreenData() }
    }
}

@Composable
private fun HomeScreen(
    homeViewModel: HomeViewModel,
    navController: NavController
) {
    val isRefreshing by homeViewModel.isRefreshing.collectAsState()
    val refreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)
    Column {
        Header(navController)
        SwipeRefresh(state = refreshState, onRefresh = homeViewModel::refreshHomeScreen) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    LargeCard(homeViewModel, navController)
                }
                item {
                    PopularSeries(homeViewModel, navController)
                }
                item {
                    TrendingMovies(homeViewModel, navController)
                }
                item {
                    TopRatedSeries(homeViewModel, navController)
                }
                item {
                    OnAirSeries(homeViewModel, navController)
                }
                item {
                    UpComingMovies(homeViewModel, navController)
                }
                item {
                    TrendingSeries(homeViewModel, navController)
                }
                item {
                    TopRatedMovies(homeViewModel, navController)
                }
                item {
                    TrendingPersons(homeViewModel, navController)
                }
                item {
                    PopularMovies(homeViewModel, navController)
                }
            }
        }
    }
}

@Composable
private fun TrendingPersons(homeViewModel: HomeViewModel, navController: NavController) {
    val trendingMovies by homeViewModel.trendingPersons.collectAsState()
    if (trendingMovies.isNotEmpty())
        Column {
            Text(
                text = stringResource(R.string.trending_persons),
                style = MaterialTheme.typography.titleMedium
            )
            LazyRow {
                items(trendingMovies) {
                    if (it.profilePath.isNullOrBlank().not())
                        MediumCard(it.profilePath ?: "", it.name) {
                            navController.navigate(Screen.CastDetail.createRoute(it.id ?: 0))
                        }
                }
            }
        }
}

@Composable
private fun Header(navController: NavController) {
    val showInfoDialog = remember { mutableStateOf(false) }
    if (showInfoDialog.value)
        AboutDialog(onDismissRequest = { showInfoDialog.value = false })

    Spacer(modifier = Modifier.height(6.dp))
    Row(
        modifier = Modifier
            .background(Color.Transparent)
            .padding(start = 9.dp, end = 9.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Row {
            IconButton(
                onClick = { showInfoDialog.value = true },
            ) {
                Icon(Icons.Outlined.Info, contentDescription = null)
            }
            IconButton(
                onClick = { navController.navigate(Screen.SearchScreen.route) },
            ) {
                Icon(Icons.Outlined.Search, contentDescription = null)
            }
        }
    }
}

@Composable
fun TrendingMovies(homeViewModel: HomeViewModel, navController: NavController) {
    val trendingMovies by homeViewModel.trendingMovies.collectAsState()
    if (trendingMovies.isNotEmpty())
        Column {
            Text(
                text = stringResource(R.string.trending_movies),
                style = MaterialTheme.typography.titleMedium
            )
            LazyRow {
                items(trendingMovies) {
                    MediumCard(it.posterPath ?: "") {
                        navController.navigate(Screen.MovieDetailScreen.createRoute(it.id ?: 0))
                    }
                }
            }
        }
}

@Composable
fun TrendingSeries(homeViewModel: HomeViewModel, navController: NavController) {
    val trendingMovies by homeViewModel.trendingSeries.collectAsState()
    if (trendingMovies.isNotEmpty())
        Column {
            Text(
                text = stringResource(R.string.trending_series),
                style = MaterialTheme.typography.titleMedium
            )
            LazyRow {
                items(trendingMovies) {
                    MediumCard(it.posterPath ?: "") {
                        navController.navigate(
                            Screen.SerieDetail.createRoute(it.id ?: 0)
                        )
                    }
                }
            }
        }
}

@Composable
fun PopularSeries(homeViewModel: HomeViewModel, navController: NavController) {
    val popularSeries by homeViewModel.popularSeries.collectAsState()
    if (popularSeries.isNotEmpty())
        Column {
            Text(
                text = stringResource(id = R.string.popular_series),
                style = MaterialTheme.typography.titleSmall
            )
            LazyRow {
                items(popularSeries) {
                    SmallCard(it.posterPath ?: "")
                    { navController.navigate(Screen.SerieDetail.createRoute(it.id ?: 0)) }
                }
            }
        }
}

@Composable
fun UpComingMovies(homeViewModel: HomeViewModel, navController: NavController) {
    val upComingMovies by homeViewModel.upComingMovies.collectAsState()
    if (upComingMovies.isNotEmpty())
        Column {
            Text(
                text = stringResource(id = R.string.upcoming_movies),
                style = MaterialTheme.typography.titleSmall
            )
            LazyRow {
                items(upComingMovies) { movie ->
                    SmallCard(movie.posterPath ?: "") {
                        navController.navigate(Screen.MovieDetailScreen.createRoute(movie.id ?: 0))
                    }
                }
            }
        }
}

@Composable
fun OnAirSeries(homeViewModel: HomeViewModel, navController: NavController) {
    val onAirSeries by homeViewModel.onAirSeries.collectAsState()
    if (onAirSeries.isNotEmpty())
        Column {
            Text(
                text = stringResource(id = R.string.on_air),
                style = MaterialTheme.typography.titleSmall
            )
            LazyRow {
                items(onAirSeries) {
                    SquareCard(
                        it.backdropPath ?: "", it.name
                    ) { navController.navigate(Screen.MovieDetailScreen.createRoute(it.id ?: 0)) }
                }
            }
        }
}

@Composable
fun TopRatedSeries(homeViewModel: HomeViewModel, navController: NavController) {
    val topRatedSeries by homeViewModel.topRatedSeries.collectAsState()
    if (topRatedSeries.isNotEmpty())
        Column {
            Text(
                text = stringResource(id = R.string.top_rated_series),
                style = MaterialTheme.typography.titleSmall
            )
            LazyRow {
                items(topRatedSeries) {
                    CardWithRate(
                        imageURL = it.posterPath ?: "",
                        String.format("%.1f", it.voteAverage)
                    ) { navController.navigate(Screen.SerieDetail.createRoute(it.id ?: 0)) }
                }
            }
        }
}

@Composable
fun TopRatedMovies(homeViewModel: HomeViewModel, navController: NavController) {
    val topRatedMovies by homeViewModel.topRatedMovies.collectAsState()
    if (topRatedMovies.isNotEmpty())
        Column {
            Text(
                text = stringResource(id = R.string.top_rated_movies),
                style = MaterialTheme.typography.titleSmall
            )
            LazyRow {
                items(topRatedMovies) { movie ->
                    CardWithRate(
                        imageURL = movie.posterPath ?: "",
                        String.format("%.1f", movie.voteAverage)
                    ) {
                        navController.navigate(Screen.MovieDetailScreen.createRoute(movie.id ?: 0))
                    }
                }
            }
        }
}

@Composable
fun PopularMovies(homeViewModel: HomeViewModel, navController: NavController) {
    val popularMovies by homeViewModel.popularMovies.collectAsState()
    if (popularMovies.isNotEmpty())
        Column {
            Text(
                text = stringResource(id = R.string.popular_movies),
                style = MaterialTheme.typography.titleSmall
            )
            LazyRow {
                items(popularMovies) {
                    SmallCard(it.posterPath ?: "")
                    { navController.navigate(Screen.MovieDetailScreen.createRoute(it.id ?: 0)) }
                }
            }
        }
}