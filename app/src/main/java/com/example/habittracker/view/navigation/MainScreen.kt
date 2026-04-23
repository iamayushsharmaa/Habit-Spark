package com.example.habittracker.view.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.font.createFontFamilyResolver
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.habittracker.view.main.AddHabit
import com.example.habittracker.view.main.Home
import com.example.habittracker.view.main.Profile
import com.example.habittracker.view.main.component.BottomNavBar
import kotlinx.coroutines.CoroutineExceptionHandler

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(outerNavController: NavHostController) {
    val innerNavController = rememberNavController()

    val handler = CoroutineExceptionHandler { _, throwable ->
        Log.e("TAG", "There has been an issue: ", throwable)
    }

    CompositionLocalProvider(
        LocalFontFamilyResolver provides createFontFamilyResolver(LocalContext.current, handler)
    ) {
        Scaffold(
            bottomBar = {
                BottomNavBar(navController = innerNavController)
            }
        ) { innerPadding ->
            NavHost(
                navController = innerNavController,
                startDestination = BottomNavItem.Home.route,
                modifier = Modifier.padding(innerPadding),
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(350, easing = FastOutSlowInEasing)
                    ) + slideInHorizontally(
                        initialOffsetX = { it / 8 },   // ✅ very subtle — 1/8 instead of 1/4
                        animationSpec = tween(350, easing = FastOutSlowInEasing)
                    )
                },
                exitTransition = {
                    fadeOut(
                        animationSpec = tween(350, easing = FastOutSlowInEasing)
                    ) + slideOutHorizontally(
                        targetOffsetX = { -it / 8 },
                        animationSpec = tween(350, easing = FastOutSlowInEasing)
                    )
                },
                popEnterTransition = {
                    fadeIn(
                        animationSpec = tween(350, easing = FastOutSlowInEasing)
                    ) + slideInHorizontally(
                        initialOffsetX = { -it / 8 },
                        animationSpec = tween(350, easing = FastOutSlowInEasing)
                    )
                },
                popExitTransition = {
                    fadeOut(
                        animationSpec = tween(350, easing = FastOutSlowInEasing)
                    ) + slideOutHorizontally(
                        targetOffsetX = { it / 8 },
                        animationSpec = tween(350, easing = FastOutSlowInEasing)
                    )

                }

            ) {
                composable(BottomNavItem.Home.route) {
                    Home(navController = outerNavController)
                }
                composable(BottomNavItem.AddHabit.route) {
                    AddHabit(
                        navController = innerNavController
                    )
                }
                composable(BottomNavItem.Profile.route) {
                    Profile(navController = outerNavController)
                }
            }
        }
    }
}

