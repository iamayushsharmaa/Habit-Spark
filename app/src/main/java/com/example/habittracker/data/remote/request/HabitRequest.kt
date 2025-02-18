package com.example.habittracker.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class HabitRequest (
    val userId: String,
    val name: String,
    val description: String,
    val icon : String,
    val iconBackground: String,
    val startDate: Long,
    val goal: GoalRequest,
    val completed: Boolean = false
)
