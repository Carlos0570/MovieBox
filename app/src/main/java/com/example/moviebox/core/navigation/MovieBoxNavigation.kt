package com.example.moviebox.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviebox.home.HomeScreenBody
import com.example.moviebox.home.HomeViewModel
import com.example.moviebox.moviedetail.MovieDetailBody
import com.example.moviebox.moviedetail.MovieDetailViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.moviebox.castDetail.CastDetailScreenBody
import com.example.moviebox.castDetail.CastViewModel
import com.example.moviebox.searchscreen.SearchScreenBody
import com.example.moviebox.searchscreen.SearchViewModel
import com.example.moviebox.serieDetail.SerieDetailScreenBody
import com.example.moviebox.serieDetail.SerieDetailViewModel

@Composable
fun MovieBoxNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {

        composable(route = Screen.HomeScreen.route) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreenBody(homeViewModel, navController)
        }

        composable(route = Screen.SearchScreen.route) {
            val searchViewModel = hiltViewModel<SearchViewModel>()
            SearchScreenBody(searchViewModel, navController)
        }

        composable(
            route = Screen.MovieDetailScreen.route,
            arguments = listOf(navArgument("movie_id") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieDetailViewModel = hiltViewModel<MovieDetailViewModel>()
            val movieId = backStackEntry.arguments?.getInt("movie_id") ?: 0

            MovieDetailBody(navController, movieDetailViewModel, movieId)
        }

        composable(
            route = Screen.SerieDetail.route,
            arguments = listOf(navArgument("serie_id") { type = NavType.IntType })
        ) { backStackEntry ->
            val serieDetailViewModel = hiltViewModel<SerieDetailViewModel>()
            val serieId = backStackEntry.arguments?.getInt("serie_id") ?: 0

            SerieDetailScreenBody(
                navController = navController,
                serieDetailViewModel = serieDetailViewModel,
                serieId = serieId
            )
        }

        composable(
            route = Screen.CastDetail.route,
            arguments = listOf(navArgument("cast_id") { type = NavType.IntType })
        ) { backStackEntry ->
            val castViewModel = hiltViewModel<CastViewModel>()
            val castId = backStackEntry.arguments?.getInt("cast_id") ?: 0

            CastDetailScreenBody(
                castViewModel = castViewModel,
                castId = castId,
                navController = navController
            )
        }
    }
}
