package com.example.habittracker.data.remote

import com.example.habittracker.data.remote.request.HabitCompletionRequest
import com.example.habittracker.data.remote.request.HabitRequest
import com.example.habittracker.data.remote.response.HabitResponse
import com.example.habittracker.data.remote.response.Resource
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface HabitsRepository {
    suspend fun createHabit(habit: HabitRequest)
    suspend fun getHabitsByDate(userId: String, date: LocalDate): Flow<Resource<List<HabitResponse>>>
    suspend fun updateHabit(habitId: String, habitCompletionRequest: HabitCompletionRequest)
    suspend fun deleteHabit(habitId: String)
}