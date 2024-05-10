package com.example.moviebox.core.navigation

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")

     object SearchScreen : Screen("search_screen")

    object MovieDetailScreen : Screen("movie_detail_screen/{movie_id}") {
        fun createRoute(movieId: Int) = "movie_detail_screen/$movieId"
    }

    object SerieDetail : Screen("serie_detail_screen/{serie_id}") {
        fun createRoute(serieId: Int) = "serie_detail_screen/$serieId"
    }

    object CastDetail : Screen("cast_detail_screen/{cast_id}") {
        fun createRoute(castId: Int) = "cast_detail_screen/$castId"
    }
}