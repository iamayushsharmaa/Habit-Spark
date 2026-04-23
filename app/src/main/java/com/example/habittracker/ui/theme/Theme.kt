package com.example.habittracker.ui.theme


import android.R.id.primary
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.example.habittracker.ui.theme.AppColor

private val LightColorsScheme = lightColorScheme(
    background = AppColor.White,
    surface = AppColor.White,
    onBackground = AppColor.Black,
    onSurface = AppColor.Black,
    surfaceVariant = AppColor.WhiteFade,
    onSurfaceVariant = AppColor.BlackFade,
    secondary = AppColor.Orange,
    onPrimary = AppColor.WhiteFade,
    primary = AppColor.Green,
    onSecondary = AppColor.Black
)

private val DarkColorsScheme = darkColorScheme(
    background = AppColor.DarkBackground,
    surface = AppColor.DarkSurface,
    onBackground = AppColor.White,
    onSurface = AppColor.White,
    surfaceVariant = AppColor.DarkWhiteFade,
    onSurfaceVariant = AppColor.DarkBlackFade,
    secondary = AppColor.Orange,
    onSecondary = AppColor.White,
    primary = AppColor.Green,
    onPrimary = AppColor.BlackFade,
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