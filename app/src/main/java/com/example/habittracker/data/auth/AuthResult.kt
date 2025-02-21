package com.example.habittracker.data.auth

sealed class AuthResult<T> {
    data class Authorized<T>(val data: T? = null) : AuthResult<T>()
    data class Unauthorized<T>(val message: String? = null) : AuthResult<T>()
    data class UnknownError<T>(val message: String? = null) : AuthResult<T>()
}