package com.example.habittracker.ui.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorsScheme = lightColorScheme(
    background = AppColor.White,
    surface = AppColor.White,
    onBackground = AppColor.Black,
    onSurface = AppColor.Black,
    surfaceVariant = AppColor.WhiteFade,
    onSurfaceVariant = AppColor.BlackFade
)

private val DarkColorsScheme = darkColorScheme(
    background = AppColor.DarkBackground,
    surface = AppColor.DarkSurface,
    onBackground = AppColor.White,
    onSurface = AppColor.White,
    surfaceVariant = AppColor.DarkWhiteFade,
    onSurfaceVariant = AppColor.DarkBlackFade
)

@Composable
fun HabitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Use system dark mode setting by default
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorsScheme else LightColorsScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}