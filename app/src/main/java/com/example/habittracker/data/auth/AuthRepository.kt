package com.example.habittracker.data.auth

interface AuthRepository {
    suspend fun signUp(username: String, password: String) : AuthResult<Unit>
    suspend fun signIn(username: String, password: String) : AuthResult<Unit>
    suspend fun getUserData() : AuthResult<UserData?>
   // suspend fun updateUserName(newName: String): AuthResult<Unit>
    suspend fun authenticate() : AuthResult<Unit>

}