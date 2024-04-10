package com.example.moviebox.serieDetail.ui

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moviebox.R
import com.example.moviebox.core.data.dataClasses.Serie
import com.example.moviebox.core.data.dataClasses.Trailer
import com.example.moviebox.core.navigation.Screen
import com.example.moviebox.core.presentation.composeComponents.LoadingAnimation
import com.example.moviebox.core.presentation.composeComponents.MediaItemCast
import com.example.moviebox.core.presentation.composeComponents.MediaItemImageHeader
import com.example.moviebox.core.presentation.screenStates.ScreenError
import com.example.moviebox.core.presentation.screenStates.ScreenState
import com.example.moviebox.core.presentation.composeComponents.WhereToWatch
import com.example.moviebox.core.presentation.composeComponents.SimilarMediaItems
import com.example.moviebox.moviedetail.ui.TrailerCard
import com.example.moviebox.youtubePlayer.ui.YoutubeScreen

@Composable
fun SerieDetailScreenBody(
    navController: NavController,
    serieDetailViewModel: SerieDetailViewModel,
    serieId: Int
) {
    LaunchedEffect(key1 = "") {
        serieDetailViewModel.initSerieDetail(serieId)
    }
    val state by serieDetailViewModel.state.collectAsState()
    when (state) {
        ScreenState.LOADING -> LoadingAnimation()
        ScreenState.SUCCESS -> SerieDetailScreen(navController, serieDetailViewModel)
        is ScreenState.FAILURE -> ScreenError { serieDetailViewModel.initSerieDetail(serieId) }
    }
}

@Composable
private fun SerieDetailScreen(
    navController: NavController,
    serieDetailViewModel: SerieDetailViewModel
) {
    val listState = rememberLazyListState()
    val serieDetail by serieDetailViewModel.serieDetail.collectAsState()
    val trailerId by serieDetailViewModel.trailerId.collectAsState()
    val showTrailer by serieDetailViewModel.showTrailer.collectAsState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            MediaItemImageHeader(serieDetail, serieDetail?.originalName ?: "", navController)
        }
        item {
            DetailsTab(
                serieDetail = serieDetail,
                navController = navController,
                serieDetailViewModel = serieDetailViewModel
            )
        }
    }
    if (showTrailer)
        trailerId?.let {
            YoutubeScreen(videoId = it) { serieDetailViewModel.dismissTrailer() }
        }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DetailsTab(
    serieDetail: Serie?,
    navController: NavController,
    serieDetailViewModel: SerieDetailViewModel
) {
    val serieCast by serieDetailViewModel.serieCast.collectAsState()
    val similarSeries by serieDetailViewModel.similarSeries.collectAsState()
    val serieProviders by serieDetailViewModel.serieProviders.collectAsState()
    val trailers by serieDetailViewModel.trailers.collectAsState()

    val tabTitles = mutableListOf<Pair<Int, String>>()
    tabTitles.add(Pair(0, stringResource(R.string.overview)))
    if (trailers.isNotEmpty())
        tabTitles.add(Pair(tabTitles.size, stringResource(id = R.string.trailers)))
    if (serieProviders?.CA != null)
        tabTitles.add(Pair(tabTitles.size, stringResource(R.string.where_to_watch)))
    if (serieCast.isNotEmpty())
        tabTitles.add(Pair(tabTitles.size, stringResource(R.string.cast)))
    if (similarSeries.isNotEmpty())
        tabTitles.add(Pair(tabTitles.size, stringResource(R.string.similar)))

    val pagersState = rememberPagerState { tabTitles.size }
    var tabIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(tabIndex) {
        pagersState.animateScrollToPage(tabIndex)
    }
    LaunchedEffect(pagersState.currentPage, pagersState.isScrollInProgress) {
        if (pagersState.isScrollInProgress.not())
            tabIndex = pagersState.currentPage
    }
    HorizontalDivider(Modifier.height(5.dp), color = MaterialTheme.colorScheme.background)
    if (tabTitles.isNotEmpty())
        Surface(
            modifier = Modifier
                .height(450.dp)
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
                                        .padding(5.dp),
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
                                SerieOverView(serieDetail)
                            }

                            stringResource(R.string.trailers) -> {
                                TrailersTab(
                                    trailers = trailers,
                                    serieDetailViewModel = serieDetailViewModel
                                )
                            }

                            stringResource(R.string.cast) -> {
                                MediaItemCast(serieCast, navController)
                            }

                            stringResource(R.string.similar) -> {
                                SimilarMediaItems(similarSeries) { id ->
                                    navController
                                        .navigate(Screen.SerieDetail.createRoute(id))
                                }
                            }

                            stringResource(R.string.where_to_watch) -> {
                                WhereToWatch(serieProviders)
                            }
                        }
                    }

                }
            }
        }
}

@Composable
private fun SerieOverView(serieDetail: Serie?) {
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

            Text(
                text = serieDetail?.overview ?: "",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "${stringResource(id = R.string.first_air_date)} ${serieDetail?.firstAirDate}",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.End
            )
            Text(
                text = "${stringResource(id = R.string.original_language)} ${serieDetail?.originalLanguage}",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.End
            )
            Text(
                text = "${stringResource(id = R.string.origin_country)} ${serieDetail?.originCountry}",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.End
            )

        }
    }
}

@Composable
private fun TrailersTab(trailers: List<Trailer>, serieDetailViewModel: SerieDetailViewModel) {
    LazyColumn {
        items(trailers) {
            if (it.youtubeData?.items?.firstOrNull() != null)
            TrailerCard(trailer = it) { key -> serieDetailViewModel.showMovieTrailer(key) }
        }
    }
}
