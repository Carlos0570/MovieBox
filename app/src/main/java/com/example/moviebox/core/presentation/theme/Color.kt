package com.example.moviebox.core.presentation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val DarkBlue = Color(0xFF17223B)
val DarkGray = Color(0xFF6B778D)

val Black = Color(0xFF000000)
val White = Color(0xFFffffff)

 val darkColorScheme = darkColorScheme(
    primary = White,
    secondary = White,
    tertiary = DarkGray,
    background = DarkBlue,
    surface = Black
)

 val lightColorScheme = lightColorScheme(
    primary = DarkBlue,
    secondary = DarkBlue,
    tertiary = DarkGray,
    background = White,
    surface = Black
)


