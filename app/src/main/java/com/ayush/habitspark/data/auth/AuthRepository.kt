package com.ayush.habitspark.data.auth

import com.ayush.habitspark.data.models.AuthResult
import com.ayush.habitspark.data.models.UserData

interface AuthRepository {

    suspend fun signUp(name: String, email: String, password: String): AuthResult<Unit>
    suspend fun signIn(email: String, password: String): AuthResult<Unit>
    suspend fun signInWithGoogle(idToken: String): AuthResult<Unit>
    suspend fun getUserData(): AuthResult<UserData?>
    fun getCurrentUserId(): String?
    fun logout()

    suspend fun updateDisplayName(name: String): AuthResult<Unit>
    fun getCurrentUserName(): String
    fun getCurrentUserEmail(): String
}