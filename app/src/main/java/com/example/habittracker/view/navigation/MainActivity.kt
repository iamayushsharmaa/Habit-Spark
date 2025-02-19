package com.example.habittracker.view.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.habittracker.ui.theme.HabitTheme
import com.example.habittracker.view.auth.LoginScreen
import com.example.habittracker.view.auth.SignUpScreen
import com.example.habittracker.viewModel.AuthViewModel
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
                val SignInViewModel = viewModel<AuthViewModel>()
                val isUserSignedIn = mAuth.currentUser != null

                NavHost(
                    navController = navController,
                    startDestination = "main_screen"
                ) {
                    composable("signin") {
                        LoginScreen(
                            navController = navController,
                        )
                    }
                    composable("signup"){
                        SignUpScreen(
                            navController
                        )
                    }
                    composable("main_screen") {
                        MainScreen()
                    }
                }
            }
        }
    }
}
