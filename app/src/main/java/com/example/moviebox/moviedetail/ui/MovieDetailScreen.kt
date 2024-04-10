package com.example.moviebox.moviedetail.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.moviebox.R
import com.example.moviebox.core.data.dataClasses.Movie
import com.example.moviebox.core.data.dataClasses.Trailer
import com.example.moviebox.core.navigation.Screen
import com.example.moviebox.core.presentation.composeComponents.LoadingAnimation
import com.example.moviebox.core.presentation.composeComponents.MediaItemCast
import com.example.moviebox.core.presentation.composeComponents.MediaItemImageHeader
import com.example.moviebox.core.presentation.screenStates.ScreenError
import com.example.moviebox.core.presentation.screenStates.ScreenState
import com.example.moviebox.core.presentation.composeComponents.WhereToWatch
import com.example.moviebox.core.presentation.composeComponents.SimilarMediaItems
import com.example.moviebox.youtubePlayer.ui.YoutubeScreen

@Composable
fun MovieDetailBody(
    navController: NavController,
    movieDetailViewModel: MovieDetailViewModel,
    movieId: Int
) {
    LaunchedEffect(key1 = "") {
        movieDetailViewModel.initMovieDetail(movieId)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        val state by movieDetailViewModel.state.collectAsState()
        when (state) {
            ScreenState.LOADING -> LoadingAnimation()
            ScreenState.SUCCESS -> MovieDetailScreen(navController, movieDetailViewModel)
            is ScreenState.FAILURE -> ScreenError { movieDetailViewModel.initMovieDetail(movieId) }
        }
    }
}


@Composable
private fun MovieDetailScreen(
    navController: NavController,
    movieDetailViewModel: MovieDetailViewModel
) {
    val listState = rememberLazyListState()
    val movieDetail by movieDetailViewModel.movieDetail.collectAsState()
    val trailerId by movieDetailViewModel.trailerId.collectAsState()
    val showTrailer by movieDetailViewModel.showTrailer.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                MediaItemImageHeader(movieDetail, movieDetail?.originalTitle ?: "", navController)
            }
            item {
                DetailsTab(movieDetailViewModel, navController)
            }
        }
        if (showTrailer)
            trailerId?.let {
                YoutubeScreen(videoId = it) { movieDetailViewModel.dismissTrailer() }
            }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DetailsTab(
    movieDetailViewModel: MovieDetailViewModel,
    navController: NavController
) {
    val movieDetail by movieDetailViewModel.movieDetail.collectAsState()
    val movieCast by movieDetailViewModel.movieCast.collectAsState()
    val similarMovies by movieDetailViewModel.similarMovies.collectAsState()
    val movieProviders by movieDetailViewModel.movieProviders.collectAsState()
    val trailers by movieDetailViewModel.trailers.collectAsState()

    val tabTitles = mutableListOf<Pair<Int, String>>()
    tabTitles.add(Pair(0, stringResource(R.string.overview)))
    if (trailers.isNotEmpty())
        tabTitles.add(Pair(tabTitles.size, stringResource(id = R.string.trailers)))
    if (movieProviders?.CA != null)
        tabTitles.add(Pair(tabTitles.size, stringResource(R.string.where_to_watch)))
    if (movieCast.isNotEmpty())
        tabTitles.add(Pair(tabTitles.size, stringResource(R.string.cast)))
    if (similarMovies.isNotEmpty())
        tabTitles.add(Pair(tabTitles.size, stringResource(R.string.similar)))

    val pagersState = rememberPagerState {
        tabTitles.size
    }
    var tabIndex by remember { mutableIntStateOf(0) }
    LaunchedEffect(tabIndex) { pagersState.animateScrollToPage(tabIndex) }
    LaunchedEffect(pagersState.currentPage, pagersState.isScrollInProgress) {
        if (pagersState.isScrollInProgress.not())
            tabIndex = pagersState.currentPage
    }
    HorizontalDivider(Modifier.height(5.dp), color = MaterialTheme.colorScheme.background)

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
                ScrollableTabRow(
                    selectedTabIndex = tabIndex,
                    containerColor = MaterialTheme.colorScheme.background
                ) {
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

                            stringResource(R.string.trailers) -> {
                                TrailersTab(trailers, movieDetailViewModel)
                            }

                            stringResource(R.string.cast) -> {
                                MediaItemCast(movieCast, navController)
                            }

                            stringResource(R.string.similar) -> {
                                SimilarMediaItems(similarMovies) { id ->
                                    navController.navigate(
                                        Screen.MovieDetailScreen.createRoute(id)
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

            if (movieDetail?.genres?.isNotEmpty() == true) {
                Row {
                    Text(
                        text = "${stringResource(R.string.genres)} ",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.End
                    )
                    movieDetail.genres.forEach {
                        Text(
                            text = "${it.name} ",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TrailersTab(trailers: List<Trailer>, movieDetailViewModel: MovieDetailViewModel) {
    LazyColumn {
        items(trailers) { trailer ->
            TrailerCard(trailer = trailer) {key -> movieDetailViewModel.showMovieTrailer(key) }
        }
    }
}

@Composable
fun TrailerCard(trailer: Trailer, showTrailer: (String) -> Unit) {
    trailer.youtubeData?.items?.first()?.snippet.let {
        Row(
            Modifier.clickable { trailer.key?.let { key -> showTrailer(key) } }) {
            Box(
                modifier = Modifier
                    .height(95.dp)
                    .width(140.dp)
                    .padding(6.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                AsyncImage(
                    model = it?.thumbnails?.high?.url ?: it?.thumbnails?.medium?.url,
                    placeholder = null,
                    error = null,
                    contentDescription = "Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 3.dp, top = 7.dp)
            ) {
                trailer.name?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier
                            .width(230.dp)
                            .padding(start = 2.dp),
                        maxLines = 1,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
                it?.channelTitle?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier
                            .width(230.dp)
                            .padding(start = 2.dp),
                        maxLines = 1,
                        fontWeight = FontWeight.SemiBold,
                    )
                }

                trailer.type?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleSmall,
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
}

