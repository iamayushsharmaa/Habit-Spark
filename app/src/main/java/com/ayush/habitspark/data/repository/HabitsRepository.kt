package com.ayush.habitspark.data.repository

import com.ayush.habitspark.data.models.HabitRequest
import com.ayush.habitspark.data.models.HabitResponse
import com.ayush.habitspark.data.models.Resource
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