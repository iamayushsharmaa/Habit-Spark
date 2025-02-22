package com.example.habittracker.data.remote.response

import java.time.LocalDate
import java.util.UUID

data class HabitCompletionResponse(
    val completionId: String = UUID.randomUUID().toString(),
    val habitId: String,
    val date: LocalDate,
    val isCompleted: Boolean,
)