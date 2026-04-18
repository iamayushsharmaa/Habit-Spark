package com.example.habittracker.data.auth

data class UserData(
    val userId: String = "",
    val name: String? = null,
    val email: String = "",
    val profileImageUrl: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)