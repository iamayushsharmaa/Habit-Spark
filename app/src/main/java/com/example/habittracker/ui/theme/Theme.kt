package com.example.habittracker.ui.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = AppColor.Black,
    onPrimary = Color.White,
    secondary = AppColor.WhiteFade,
    background = AppColor.White,
    surface = AppColor.White,
)

private val DarkColors = darkColorScheme(
    primary = AppColor.White,
    onPrimary = Color.White,
    secondary = AppColor.BlackFade,
    background = AppColor.Black,
    surface = AppColor.Black,
)

@Composable
fun HabitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Use system dark mode setting by default
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}