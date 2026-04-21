package com.example.habittracker.view.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.habittracker.ui.theme.HabitTheme
import com.example.habittracker.view.auth.SignInScreen
import com.example.habittracker.view.auth.SignUpScreen
import com.example.habittracker.view.main.UpdatePassword
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HabitTheme {
                val navController = rememberNavController()
                val isUserSignedIn = FirebaseAuth.getInstance().currentUser != null

                val startDestination = if (isUserSignedIn) {
                    Screen.Main.route
                } else {
                    Screen.SignIn.route
                }

                NavHost(
                    navController = navController,
                    startDestination = startDestination
                ) {
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
}
