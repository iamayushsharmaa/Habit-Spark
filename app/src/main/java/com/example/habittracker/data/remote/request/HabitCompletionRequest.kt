package com.example.habittracker.data.remote.request

import java.time.LocalDate
import java.util.UUID

data class HabitCompletionRequest(
    val completionId: String = UUID.randomUUID().toString(),
    val habitId: String,
    val date: LocalDate,
    val isCompleted: Boolean
)