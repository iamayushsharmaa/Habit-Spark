package com.ayush.habitspark.mappers

import com.ayush.habitspark.data.models.GoalDto
import com.ayush.habitspark.data.models.HabitDto
import com.ayush.habitspark.data.models.Frequency
import com.ayush.habitspark.data.models.Goal
import com.ayush.habitspark.data.models.HabitRequest
import com.ayush.habitspark.data.models.HabitCompletion
import com.ayush.habitspark.data.models.HabitResponse

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