package com.example.habittracker.data.auth

interface AuthRepository {

    suspend fun signUp(name: String, email: String, password: String): AuthResult<Unit>

    suspend fun signIn(email: String, password: String): AuthResult<Unit>

    suspend fun signInWithGoogle(idToken: String): AuthResult<Unit>

    suspend fun getUserData(): AuthResult<UserData?>

    fun getCurrentUserId(): String?

    fun logout()
}