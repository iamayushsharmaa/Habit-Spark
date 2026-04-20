package com.example.habittracker.data.models

import com.example.habittracker.utils.getTodayTimestamp

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
