package com.example.habittracker.mappers

import com.example.habittracker.data.models.GoalDto
import com.example.habittracker.data.models.HabitDto
import com.example.habittracker.data.models.Frequency
import com.example.habittracker.data.models.Goal
import com.example.habittracker.data.models.HabitRequest
import com.example.habittracker.data.models.HabitCompletion
import com.example.habittracker.data.models.HabitResponse

fun HabitRequest.toDto(habitId: String): HabitDto {
    return HabitDto(
        habitId = habitId,
        name = name,
        icon = icon,
        iconBackground = iconBackground,
        description = description,
        goal = GoalDto(goal.value, goal.unit),
        frequency = frequency.name,
        startDate = startDate,
        isActive = isActive,
        isLocked = false,
        completionHistory = emptyList()
    )
}

fun HabitDto.toDomain(): HabitResponse {
    return HabitResponse(
        habitId = habitId,
        name = name,
        icon = icon,
        iconBackground = iconBackground,
        description = description,
        goal = Goal(goal.value, goal.unit),
        frequency = Frequency.valueOf(frequency),
        startDate = startDate,
        isActive = isActive,
        isLocked = isLocked,
        completionHistory = completionHistory.map {
            HabitCompletion(it.date, it.isCompleted)
        }
    )
}