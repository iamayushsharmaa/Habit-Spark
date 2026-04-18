package com.example.habittracker.view.auth.component

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.habittracker.core.auth.provideGoogleSignInClient
import com.example.habittracker.viewModel.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

@Composable
fun GoogleSignInButton(
    viewModel: AuthViewModel
) {
    val context = LocalContext.current
    val googleClient = remember {
        provideGoogleSignInClient(context)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->

        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

            try {
                val account = task.getResult(ApiException::class.java)
                val idToken = account.idToken

                if (idToken != null) {
                    viewModel.signInWithGoogle(idToken)
                } else {
                    // handle error
                }

            } catch (e: Exception) {
                // handle error
            }
        }
    }

    Button(onClick = {
        launcher.launch(googleClient.signInIntent)
    }) {
        Text("Continue with Google")
    }
}