package com.example.moviebox.moviedetail.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moviebox.R
import com.example.moviebox.core.data.dataClasses.Cast
import com.example.moviebox.core.data.dataClasses.Movie
import com.example.moviebox.core.data.dataClasses.ProvidersByCountry
import com.example.moviebox.core.navigation.Screen
import com.example.moviebox.core.presentation.composeComponents.LoadingAnimation
import com.example.moviebox.core.presentation.composeComponents.MediaItemCast
import com.example.moviebox.core.presentation.composeComponents.MediaItemImageHeader
import com.example.moviebox.core.presentation.screenStates.ScreenError
import com.example.moviebox.core.presentation.screenStates.ScreenState
import com.example.moviebox.core.presentation.composeComponents.WhereToWatch
import com.example.moviebox.core.presentation.composeComponents.SimilarMediaItems

@Composable
fun MovieDetailBody(
    navController: NavController,
    movieDetailViewModel: MovieDetailViewModel,
    movieId: Int
) {
    LaunchedEffect(key1 = "") {
        movieDetailViewModel.initMovieDetail(movieId)
    }
    val state by movieDetailViewModel.state.collectAsState()
    when (state) {
        ScreenState.LOADING -> LoadingAnimation()
        ScreenState.SUCCESS -> MovieDetailScreen(navController, movieDetailViewModel)
        is ScreenState.FAILURE -> ScreenError { movieDetailViewModel.initMovieDetail(movieId) }
    }
}

@Composable
private fun MovieDetailScreen(
    navController: NavController,
    movieDetailViewModel: MovieDetailViewModel
) {
    val listState = rememberLazyListState()
    val movieDetail by movieDetailViewModel.movieDetail.collectAsState()
    val movieCast by movieDetailViewModel.movieCast.collectAsState()
    val similarMovies by movieDetailViewModel.similarMovies.collectAsState()
    val movieProviders by movieDetailViewModel.movieProviders.collectAsState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            MediaItemImageHeader(movieDetail, movieDetail?.originalTitle ?: "", navController)
        }
        item {
            DetailsTab(movieDetail, movieCast, similarMovies, movieProviders, navController)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DetailsTab(
    movieDetail: Movie?,
    movieCast: List<Cast>,
    similarMovies: List<Movie>,
    movieProviders: ProvidersByCountry?,
    navController: NavController
) {
    val tabTitles = mutableListOf<Pair<Int, String>>()
    tabTitles.add(Pair(0, stringResource(R.string.overview)))
    if (movieCast.isNotEmpty())
        tabTitles.add(Pair(tabTitles.size, stringResource(R.string.cast)))
    if (similarMovies.isNotEmpty())
        tabTitles.add(Pair(tabTitles.size, stringResource(R.string.similar)))
    if (movieProviders?.CA != null)
        tabTitles.add(Pair(tabTitles.size, stringResource(R.string.where_to_watch)))

    val pagersState = rememberPagerState {
        tabTitles.size
    }
    var tabIndex by remember { mutableStateOf(0) }
    LaunchedEffect(tabIndex) { pagersState.animateScrollToPage(tabIndex) }
    LaunchedEffect(pagersState.currentPage, pagersState.isScrollInProgress) {
        if (pagersState.isScrollInProgress.not())
            tabIndex = pagersState.currentPage
    }
    Divider(Modifier.height(20.dp), color = MaterialTheme.colorScheme.background)
    if (tabTitles.isNotEmpty())
        Surface(
            modifier = Modifier
                .height(600.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                TabRow(selectedTabIndex = tabIndex) {
                    tabTitles.forEachIndexed { index, title ->
                        Tab(
                            content = {
                                Text(
                                    text = title.second,
                                    modifier = Modifier
                                        .background(MaterialTheme.colorScheme.background)
                                        .padding(bottom = 5.dp),
                                    style = MaterialTheme.typography.titleSmall,
                                    textAlign = TextAlign.Center
                                )
                            },
                            selected = tabIndex == index,
                            onClick = { tabIndex = tabTitles[index].first },
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.background)
                                .padding(bottom = 2.dp)
                        )
                    }
                }
                HorizontalPager(
                    state = pagersState,
                    modifier = Modifier.fillMaxWidth()
                ) { index ->
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start
                    ) {
                        when (tabTitles[index].second) {
                            stringResource(R.string.overview) -> {
                                MovieDescription(movieDetail)
                            }

                            stringResource(R.string.cast) -> {
                                MediaItemCast(movieCast, navController)
                            }

                            stringResource(R.string.similar) -> {
                                SimilarMediaItems(similarMovies) { id ->
                                    navController.navigate(
                                        Screen.MovieDetailScreen.createRoute(
                                            id
                                        )
                                    )
                                }
                            }

                            stringResource(R.string.where_to_watch) -> {
                                WhereToWatch(movieProviders)
                            }
                        }
                    }
                }
            }
        }
}

@Composable
private fun MovieDescription(movieDetail: Movie?) {
    Spacer(
        modifier = Modifier
            .height(15.dp)
            .background(Color.Green)
    )
    LazyColumn(
        modifier = Modifier
            .padding(9.dp)
            .fillMaxSize(),
    ) {
        item {
            if (movieDetail?.tagline?.isNotEmpty() == true) {
                Text(
                    text = "\"${movieDetail.tagline}\"",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Start,
                    fontStyle = FontStyle.Italic
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            Text(
                text = movieDetail?.overview ?: "",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "${stringResource(R.string.release_date)} ${movieDetail?.releaseDate}",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.End
            )
            Text(
                text = "${stringResource(R.string.original_language)} ${movieDetail?.originalLanguage}",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.End
            )
        }
    }
}

