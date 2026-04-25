package com.ayush.habitspark.view.navigation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ayush.habitspark.core.notifications.NotificationHelper
import com.ayush.habitspark.core.notifications.NotificationScheduler
import com.ayush.habitspark.core.onboarding.OnboardingPreference
import com.ayush.habitspark.ui.theme.HabitTheme
import com.ayush.habitspark.view.auth.SignInScreen
import com.ayush.habitspark.view.auth.SignUpScreen
import com.ayush.habitspark.view.main.UpdatePassword
import com.ayush.habitspark.view.onboarding.OnBoardingScreen
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        NotificationHelper.createNotificationChannel(this)

        NotificationScheduler.scheduleDailyReminder(this)
        NotificationScheduler.scheduleStreakRiskReminder(this)

        setContent {
            RequestNotificationPermission()
            HabitTheme {
                val navController = rememberNavController()
                val context = LocalContext.current
                val isUserSignedIn = FirebaseAuth.getInstance().currentUser != null
                val onboardingDone = OnboardingPreference.isOnboardingDone(context)


                val startDestination = when {
                    !onboardingDone -> Screen.Onboarding.route
                    isUserSignedIn -> Screen.Main.route
                    else -> Screen.SignIn.route
                }

                NavHost(
                    navController = navController,
                    startDestination = startDestination,
                    enterTransition = {
                        fadeIn(
                            animationSpec = tween(350, easing = FastOutSlowInEasing)
                        ) + slideInHorizontally(
                            initialOffsetX = { it / 8 },
                            animationSpec = tween(300, easing = FastOutSlowInEasing)
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

                    composable(Screen.Onboarding.route) {
                        OnBoardingScreen(
                            onFinished = {
                                navController.navigate(
                                    if (isUserSignedIn) Screen.Main.route
                                    else Screen.SignIn.route
                                ) {
                                    popUpTo(Screen.Onboarding.route) { inclusive = true }
                                }
                            }
                        )
                    }

                    composable(Screen.SignIn.route) {
                        SignInScreen(
                            navController = navController,
                        )
                    }
                    composable(Screen.SignUp.route) {
                        SignUpScreen(
                            navController
                        )
                    }
                    composable(Screen.Main.route) {
                        MainScreen(navController)
                    }

                    composable(Screen.UpdatePassword.route) {
                        UpdatePassword(navController = navController)
                    }
                }
            }
        }
    }

    @Composable
    fun RequestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val launcher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { _ -> }

            LaunchedEffect(Unit) {
                launcher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}
