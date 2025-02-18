package com.example.habittracker.data.remote.response

data class HabitResponse (
    val userId: String,
    val name: String,
    val description: String,
    val icon : String,
    val iconBackground: String,
    val startDate: Long,
    val goal: Goal,
    val completed: Boolean = false
)
