package com.example.habittracker.data.remote.response

import com.example.habittracker.data.remote.request.Frequency
import java.time.LocalDate

data class HabitResponse(
    val habitId: String,
    val userId: String,
    val name: String,
    val icon: String,
    val iconBackground: String,
    val description: String,
    val frequency: Frequency,
    val startDate: LocalDate,
    val isActive: Boolean,
    val isLocked: Boolean,
    val isCompleted: Boolean
)