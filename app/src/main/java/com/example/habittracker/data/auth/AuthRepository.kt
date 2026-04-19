package com.example.habittracker.data.auth

import com.example.habittracker.data.models.AuthResult
import com.example.habittracker.data.models.UserData

interface AuthRepository {

    suspend fun signUp(name: String, email: String, password: String): AuthResult<Unit>

    suspend fun signIn(email: String, password: String): AuthResult<Unit>

    suspend fun signInWithGoogle(idToken: String): AuthResult<Unit>

    suspend fun getUserData(): AuthResult<UserData?>

    fun getCurrentUserId(): String?

    fun logout()
}