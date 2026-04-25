package com.example.habittracker.data.models

import java.time.Instant
import java.time.ZoneId
import java.time.temporal.ChronoUnit


data class HabitResponse(
    val habitId: String,
    val name: String,
    val icon: String,
    val iconBackground: String,
    val description: String,
    val goal: Goal,
    val frequency: Frequency,
    val startDate: Long,
    val isActive: Boolean,
    val isLocked: Boolean,
    val completionHistory: List<HabitCompletion>
)

data class HabitCompletion(
    val date: Long,
    val isCompleted: Boolean
)

fun HabitResponse.isCompletedOn(date: Long): Boolean {
    return completionHistory.any {
        it.date == date && it.isCompleted
    }
}

fun HabitResponse.isScheduledFor(date: Long): Boolean {
    val startLocal = Instant.ofEpochMilli(startDate)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    val dateLocal = Instant.ofEpochMilli(date)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    if (dateLocal.isBefore(startLocal)) return false

    return when (frequency) {
        Frequency.EVERYDAY -> true

        Frequency.ALTERNATE -> {
            val daysBetween = ChronoUnit.DAYS.between(startLocal, dateLocal)
            daysBetween % 2 == 0L
        }

        Frequency.WEEKLY -> {
            dateLocal.dayOfWeek == startLocal.dayOfWeek
        }

        Frequency.MONTHLY -> {
            dateLocal.dayOfMonth == startLocal.dayOfMonth
        }
    }
}

