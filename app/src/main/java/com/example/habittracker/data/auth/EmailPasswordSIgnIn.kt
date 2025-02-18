package com.example.habittracker.data.auth

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.text.TextUtils
import androidx.navigation.NavController
import com.example.habittracker.R
import com.example.habittracker.data.SignInResult
import com.example.habittracker.data.UserData
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException


suspend fun signInWithEmailPassword(
    email: String,
    password: String,
    auth: FirebaseAuth,
    navController: NavController,
    onSuccess: () -> Unit,
    onFailure: (String) -> Unit
) {
    if (TextUtils.isEmpty(email)) {
        onFailure("Please enter email.")  // Call failure callback
        return
    }

    if (TextUtils.isEmpty(password)) {
        onFailure("Please enter password.")  // Call failure callback
        return
    }

    try {
        // Try signing in the user
        auth.signInWithEmailAndPassword(email, password).await()

        // Call success callback if the sign-in is successful
        onSuccess()
    } catch (e: Exception) {
        // Call failure callback if something goes wrong
        onFailure(e.message ?: "Login failed! Please try again later.")
    }
}

suspend fun createUserWithEmailPassword(
    email: String,
    password: String,
    auth: FirebaseAuth,
    navController: NavController,
    onSuccess: () -> Unit,
    onFailure: (String) -> Unit
) {
    if (email.isEmpty()) {
        onFailure("Please enter email.")
        return
    }

    if (password.isEmpty()) {
        onFailure("Please enter password.")
        return
    }

    if (password.length < 6) {
        onFailure("Password should be at least 6 characters long.")
        return
    }

    try {
        auth.createUserWithEmailAndPassword(email, password).await()
        onSuccess()
    } catch (e: Exception) {
        onFailure(e.message ?: "Registration failed! Please try again later.")
    }
}

class GoogleAuthUiClient (
    private val context: Context,
    private val oneTapClient : SignInClient
){

    private val auth = Firebase.auth

    suspend fun SignIn(): IntentSender?{
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        }catch (e : Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    fun getSignedInUser(): UserData? = auth.currentUser?.run {
        UserData(
            userId = uid,
            userName = displayName,
            profilePhoto = photoUrl?.toString()
        )
    }
    suspend fun SignInWithIntent(intent : Intent): SignInResult {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredential).await().user
            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        userName = displayName,
                        profilePhoto = photoUrl?.toString()
                    )
                },
                errorMessage = null
            )
        }catch (e : Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMessage = e.message
            )
        }
    }
    private fun buildSignInRequest(): BeginSignInRequest {

        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }

    suspend fun signOut(){
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        }catch (e : Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e
            null
        }
    }

}