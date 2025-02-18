package com.example.habittracker.data.auth

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val token: String
)
