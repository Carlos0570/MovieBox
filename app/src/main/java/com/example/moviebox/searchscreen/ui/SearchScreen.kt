package com.example.moviebox.searchscreen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.moviebox.R
import com.example.moviebox.core.data.dataClasses.MediaItem
import com.example.moviebox.core.data.dataClasses.Movie
import com.example.moviebox.core.data.dataClasses.Person
import com.example.moviebox.core.navigation.Screen
import com.example.moviebox.core.presentation.composeComponents.LoadingAnimation
import com.example.moviebox.core.presentation.screenStates.ScreenError
import com.example.moviebox.core.presentation.screenStates.SearchScreenState
import com.example.moviebox.home.ui.SmallCard

@Composable
fun SearchScreenBody(searchViewModel: SearchViewModel, navController: NavController) {
    val focusRequester = remember { FocusRequester() }
    val state by searchViewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        searchViewModel.init()
        focusRequester.requestFocus()
    }
    Column {
        SearchTextField(searchViewModel, navController, focusRequester)
        when (state) {
            is SearchScreenState.FAILURE -> ScreenError { searchViewModel.init() }
            SearchScreenState.LOADING -> LoadingAnimation()
            SearchScreenState.NOT_FOUND -> NotFoundAnimation()
            SearchScreenState.SUCCESS -> SearchScreen(searchViewModel, navController)
            SearchScreenState.START_SCREEN -> Recommendations(searchViewModel, navController)
        }
    }
}

@Composable
private fun SearchScreen(
    searchViewModel: SearchViewModel,
    navController: NavController
) {
    val movies by searchViewModel.movies.collectAsState()
    val series by searchViewModel.series.collectAsState()
    val persons by searchViewModel.persons.collectAsState()
    Column(Modifier.padding(7.dp)) {
        SearchResult(movies, stringResource(id = R.string.movies)) { id ->
            navController.navigate(Screen.MovieDetailScreen.createRoute(id))
        }
        SearchResult(series, stringResource(id = R.string.series)) { id ->
            navController.navigate(Screen.SerieDetail.createRoute(id))
        }
        SearchPersonResult(persons) {
            navController.navigate(Screen.CastDetail.createRoute(it))
        }
    }
}

@Composable
private fun SearchTextField(
    searchViewModel: SearchViewModel,
    navController: NavController,
    focusRequester: FocusRequester
) {
    val searchText by searchViewModel.searchText.collectAsState()
    Row(
        modifier = Modifier.padding(end = 7.dp, top = 7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
        ) {
            Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = null)
        }
        Card {
            Row(
                Modifier
                    .height(IntrinsicSize.Min)
                    .background(MaterialTheme.colorScheme.primary),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = TextFieldValue(
                        text = searchText,
                        selection = TextRange(searchText.length)
                    ),
                    onValueChange = { searchViewModel.onSearchTextChange(it.text) },
                    Modifier
                        .weight(1f)
                        .padding(start = 8.dp, end = 8.dp)
                        .focusRequester(focusRequester),
                    textStyle = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.background),
                    maxLines = 1,
                )
                Box(
                    Modifier
                        .padding(vertical = 6.dp)
                        .width(1.dp)
                        .fillMaxHeight()
                        .background(MaterialTheme.colorScheme.tertiary.copy(alpha = .5f))
                )
                Icon(imageVector = Icons.Outlined.Clear,
                    contentDescription = null,
                    Modifier
                        .padding(9.dp)
                        .clickable {
                            searchViewModel.clearData()
                        }
                )
            }
        }
    }
    Spacer(Modifier.height(12.dp))
}

@Composable
private fun Recommendations(searchViewModel: SearchViewModel, navController: NavController) {
    val popularMovies by searchViewModel.popularMovies.collectAsState()
    if (popularMovies.isNotEmpty()) {
        Column(Modifier.padding(7.dp)) {
            Text(
                text = stringResource(id = R.string.recommendations),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .width(230.dp)
                    .padding(start = 2.dp),
                maxLines = 1,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(popularMovies) { movie ->
                    PopularMovieCard(movie, navController)
                }
            }
        }
    }
}

@Composable
private fun PopularMovieCard(movie: Movie, navController: NavController) {
    Row {
        Box(
            modifier = Modifier
                .padding(5.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            AsyncImage(
                model = "${stringResource(id = R.string.image_url_500)}${movie.backdropPath}",
                placeholder = null,
                error = null,
                contentDescription = "Image",
                modifier = Modifier
                    .height(100.dp)
                    .width(150.dp)
                    .clickable {
                        movie.id?.let {
                            navController.navigate(Screen.MovieDetailScreen.createRoute(it))
                        }
                    },
                contentScale = ContentScale.Crop
            )
        }
        Column {
            movie.originalTitle?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .width(230.dp)
                        .padding(start = 2.dp)
                        .clickable {
                            movie.id?.let { id ->
                                navController.navigate(Screen.MovieDetailScreen.createRoute(id))
                            }
                        },
                    maxLines = 1,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            movie.releaseDate?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .width(230.dp)
                        .padding(start = 2.dp)
                        .clickable {
                            movie.id?.let { id ->
                                navController.navigate(Screen.MovieDetailScreen.createRoute(id))
                            }
                        },
                    maxLines = 1,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}

@Composable
private fun SearchResult(mediaItems: List<MediaItem>, title: String, onClick: (Int) -> Unit) {
    if (mediaItems.isNotEmpty()) {
        Text(text = title)
        LazyRow {
            items(mediaItems) { movie ->
                movie.posterPath?.let {
                    SmallCard(imageURL = movie.posterPath ?: movie.backdropPath ?: it) {
                        onClick(movie.id ?: 0)
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchPersonResult(persons: List<Person>, onClick: (Int) -> Unit) {
    if (persons.isNotEmpty()) {
        Text(text = stringResource(id = R.string.actors))
        LazyRow {
            items(persons) { person ->
                PersonCard(person) {
                    onClick(person.id ?: 0)
                }
            }
        }
    }
}

@Composable
private fun PersonCard(person: Person, onClick: () -> Unit) {
    if (person.profilePath != null)
        Box(
            Modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(MaterialTheme.colorScheme.tertiary)
                .clickable { onClick.invoke() }
        ) {
            Column {
                AsyncImage(
                    model = "${stringResource(id = R.string.image_url_200)}${person.profilePath}",
                    placeholder = null,
                    error = null,
                    contentDescription = "",
                    modifier = Modifier.height(160.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = person.name ?: "",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .fillMaxWidth(),
                    maxLines = 1,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
}
