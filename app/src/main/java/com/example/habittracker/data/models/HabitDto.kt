package com.example.habittracker.data.models

data class HabitDto(
    val habitId: String = "",
    val name: String = "",
    val icon: String = "",
    val iconBackground: String = "",
    val description: String = "",
    val goal: GoalDto = GoalDto(),
    val frequency: String = "",
    val startDate: Long = 0L,
    val isActive: Boolean = true,
    val isLocked: Boolean = false,
    val completionHistory: List<HabitCompletionDto> = emptyList()
)

data class GoalDto(
    val value: String = "",
    val unit: String = ""
)

data class HabitCompletionDto(
    val date: Long = 0L,
    val isCompleted: Boolean = false
)