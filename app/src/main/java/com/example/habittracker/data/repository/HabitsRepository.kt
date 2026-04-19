package com.example.habittracker.data.repository

import com.example.habittracker.data.models.HabitRequest
import com.example.habittracker.data.models.HabitResponse
import com.example.habittracker.data.models.Resource
import kotlinx.coroutines.flow.Flow

interface HabitsRepository {

    suspend fun createHabit(habit: HabitRequest)

    fun getHabits(): Flow<Resource<List<HabitResponse>>>

    suspend fun toggleHabitCompletion(
        habitId: String,
        date: Long
    )

    suspend fun deleteHabit(habitId: String)
}