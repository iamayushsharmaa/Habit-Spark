package com.example.habittracker.data.remote.request

import java.time.LocalDate

data class HabitCompletionRequest(
    val completionId: String,
    val habitId: String,
    val userId: String,
    val date: LocalDate,
    val isCompleted: Boolean
)