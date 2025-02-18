package com.example.habittracker.view.auth

data class AuthState(
    val isLoading: Boolean = false,
    val signupUsername: String = "",
    val signupPassword: String = "",
    val signinUsername: String = "",
    val signinPassword: String = ""
)
