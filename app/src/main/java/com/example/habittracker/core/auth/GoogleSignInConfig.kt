@file:Suppress("DEPRECATION")

package com.example.habittracker.core.auth

import android.content.Context
import com.example.habittracker.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

fun provideGoogleSignInClient(context: Context): GoogleSignInClient {
    val clientId = context.getString(R.string.web_client_id)

    val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(clientId)
        .requestEmail()
        .build()

    return GoogleSignIn.getClient(context, options)
}