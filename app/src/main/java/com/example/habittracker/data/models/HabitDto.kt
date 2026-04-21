package com.example.habittracker.data.models

import com.google.firebase.firestore.PropertyName

data class HabitDto(
    val habitId: String = "",
    val name: String = "",
    val icon: String = "",
    val iconBackground: String = "",
    val description: String = "",
    val goal: GoalDto = GoalDto(),
    val frequency: String = "",
    val startDate: Long = 0L,

    @get:PropertyName("active")
    @set:PropertyName("active")
    var isActive: Boolean = true,

    @get:PropertyName("locked")
    @set:PropertyName("locked")
    var isLocked: Boolean = false,
    val completionHistory: List<HabitCompletionDto> = emptyList()
)

data class GoalDto(
    val value: String = "",
    val unit: String = ""
)

data class HabitCompletionDto(
    val date: Long = 0L,

    @get:PropertyName("completed")
    @set:PropertyName("completed")
    var isCompleted: Boolean = false

)