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
import com.example.habittracker.data.auth.GoogleAuthUiClient
import com.example.habittracker.view.auth.LoginScreen
import com.example.habittracker.view.auth.SignUpScreen
import com.example.habittracker.viewModel.AuthViewModel
import com.example.habittracker.viewModel.FirestoreViewModel
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private  val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }
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
                val firestoreViewModel: FirestoreViewModel by viewModels()
                val isUserSignedIn = mAuth.currentUser != null

                NavHost(
                    navController = navController,
                    startDestination = "main_screen"
                ) {
                    composable("signin") {
//                        val launcher = rememberLauncherForActivityResult(
//                            contract = ActivityResultContracts.StartIntentSenderForResult(),
//                            onResult = { result ->
//                                if (result.resultCode == RESULT_OK) {
//                                    lifecycleScope.launch {
//                                        val signInResult =
//                                            googleAuthUiClient.SignInWithIntent(
//                                                intent = result.data ?: return@launch
//                                            )
//                                       // SignInViewModel.onSignInResult(signInResult)
//                                    }
//                                }
//                            }
//                        )
//                        LaunchedEffect(key1 = state.isSignInSuccesful) {
//                            if (state.isSignInSuccesful) {
//                                Toast.makeText(
//                                    applicationContext,
//                                    "Sign in successfully",
//                                    Toast.LENGTH_LONG
//                                ).show()
//                                navController.navigate("main_screen")
//                                SignInViewModel.resetState()
//                            }
//                        }
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
                        MainScreen(googleAuthUiClient)
                    }
                }
            }
        }
    }
}
