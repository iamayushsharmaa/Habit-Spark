package com.example.habittracker.view.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.habittracker.data.auth.AuthResult
import com.example.habittracker.ui.theme.HabitTheme
import com.example.habittracker.view.auth.SignInScreen
import com.example.habittracker.view.auth.SignUpScreen
import com.example.habittracker.view.main.UpdatePassword
import com.example.habittracker.viewModel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private lateinit var mAuth : FirebaseAuth
    val db= Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        mAuth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HabitTheme {
                val navController = rememberNavController()
                val authViewModel = viewModel<AuthViewModel>()
                val isUserSignedIn = mAuth.currentUser != null

                val authResult by authViewModel.authResult.collectAsState(initial = null)

                val startDestination = when (authResult) {
                    is AuthResult.Authorized -> "main_screen"
                    is AuthResult.Unauthorized, is AuthResult.UnknownError, null -> "signup"
                }

                NavHost(
                    navController = navController,
                    startDestination = startDestination
                ) {
                    composable("signin") {
                        SignInScreen(
                            navController = navController,
                        )
                    }
                    composable("signup"){
                        SignUpScreen(
                            navController
                        )
                    }
                    composable("main_screen") {
                        MainScreen(navController)
                    }

                    composable("update_password") {
                        UpdatePassword(navController = navController)
                    }
                }
            }
        }
    }
}
