package com.example.habittracker.data.remote.request

import java.time.LocalDate

data class HabitRequest(
    val habitId: String,
    val name: String,
    val icon : String,
    val iconBackground: String,
    val description: String,
    val frequency: Frequency,
    val startDate: LocalDate,
    val isActive: Boolean
)

enum class Frequency {
    EVERYDAY,
    ALTERNATE,
    WEEKLY,
    MONTHLY
}

fun Frequency.toDisplayString(): String {
    return when (this) {
        Frequency.EVERYDAY -> "Everyday"
        Frequency.ALTERNATE -> "Alternative"
        Frequency.WEEKLY -> "Weekly"
        Frequency.MONTHLY -> "Monthly"
    }
}