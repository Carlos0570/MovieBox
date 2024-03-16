package com.example.moviebox.castDetail.ui

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
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.moviebox.R
import com.example.moviebox.core.data.dataClasses.Credits
import com.example.moviebox.core.data.dataClasses.MediaType
import com.example.moviebox.core.data.dataClasses.Person
import com.example.moviebox.core.navigation.Screen
import com.example.moviebox.core.presentation.composeComponents.LoadingAnimation
import com.example.moviebox.core.presentation.screenStates.ScreenError
import com.example.moviebox.core.presentation.screenStates.ScreenState
import com.example.moviebox.core.presentation.composeComponents.SimpleImageCard

@Composable
fun CastDetailScreenBody(
    castViewModel: CastViewModel,
    castId: Int,
    navController: NavHostController
) {
    LaunchedEffect(key1 = "") {
        castViewModel.initCastDetail(castId)
    }
    val state by castViewModel.state.collectAsState()
    when (state) {
        ScreenState.LOADING -> LoadingAnimation()
        ScreenState.SUCCESS -> CastScreen(navController, castViewModel)
        is ScreenState.FAILURE -> ScreenError { castViewModel.initCastDetail(castId) }
    }
}

@Composable
private fun CastScreen(
    navController: NavHostController,
    castViewModel: CastViewModel
) {
    val personDetails by castViewModel.personDetails.collectAsState()
    val personCredits by castViewModel.personCredits.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            PersonImageHeader(person = personDetails, navController)
        }
        item {
            DetailsTab(
                person = personDetails,
                credits = personCredits,
                navController = navController
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DetailsTab(
    person: Person?,
    credits: Credits?,
    navController: NavController
) {
    val tabTitles = mutableListOf<Pair<Int, String>>()
    tabTitles.add(Pair(0, stringResource(R.string.overview)))
    if (credits != null)
        tabTitles.add(Pair(tabTitles.size, stringResource(R.string.known_for)))

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
                .fillMaxWidth()
                .height(600.dp)
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
                                PersonDescription(person)
                            }

                            stringResource(R.string.known_for) -> {
                                PersonCredits(credits = credits, navController = navController)
                            }
                        }
                    }
                }
            }
        }
}

@Composable
private fun PersonDescription(person: Person?) {
    Spacer(modifier = Modifier.height(14.dp))
    person?.let {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            item {
                Text(
                    text = it.biography ?: "",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                it.birthday?.let { birthday ->
                    Text(text = birthday)
                }
                it.placeOfBirth?.let { placeOfBirth ->
                    Text(text = placeOfBirth)
                }
            }
        }
    }
}

@Composable
private fun PersonCredits(credits: Credits?, navController: NavController) {
    Spacer(modifier = Modifier.height(14.dp))
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier
            .padding(9.dp)
    ) {
        items(credits?.cast ?: emptyList()) {
            SimpleImageCard(posterPath = it.posterPath) {
                it.mediaType?.let { mediaType ->
                    when (mediaType) {
                        MediaType.MOVIE -> navController.navigate(
                            Screen.MovieDetailScreen.createRoute(
                                it.id ?: 0
                            )
                        )

                        MediaType.TV -> navController.navigate(
                            Screen.SerieDetail.createRoute(
                                it.id ?: 0
                            )
                        )
                    }
                }
            }
        }
    }
}
