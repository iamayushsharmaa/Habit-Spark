package com.example.habittracker.data

data class SigninState(
    val isSignInSuccesful :Boolean = false,
    val signInError:String? = null
)