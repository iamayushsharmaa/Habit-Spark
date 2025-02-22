package com.example.habittracker.data.remote

import android.content.SharedPreferences
import android.util.Log
import com.example.habittracker.data.remote.request.HabitCompletionRequest
import com.example.habittracker.data.remote.request.HabitRequest
import com.example.habittracker.data.remote.response.HabitResponse
import com.example.habittracker.data.remote.response.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.io.IOException
import retrofit2.HttpException
import java.time.LocalDate
import javax.inject.Inject

class HabitsRepositoryImpl @Inject constructor(
    private val habitApi: HabitApi,
    private val sharedPrefs: SharedPreferences
): HabitsRepository {

    override suspend fun createHabit(habit: HabitRequest){

        try {
            val jwtToken = sharedPrefs.getString("jwt", null) ?: throw Exception("User not authenticated")
            habitApi.createHabit(habit, "Bearer $jwtToken")
        } catch (e: HttpException) {
            Log.d("TAG", "createHabit: io error $e")
        } catch (e: IOException) {
            Log.d("TAG", "createHabit: io error $e")
        }
    }

    override suspend fun getHabitsByDate(
        date: LocalDate
    ): Flow<Resource<List<HabitResponse>>>  = flow{

        emit(Resource.Loading)
        try {
            val token = sharedPrefs.getString("jwt", null) ?: throw Exception("User not authenticated")
            val habits = habitApi.getHabitsByDate("Bearer $token", date)
            emit(Resource.Success(habits))
        } catch (e: HttpException) {
            emit(Resource.Error("An unexpected error occurred: ${e.message}", e))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach the server. Check your internet connection.", e))
        }
    }


    override suspend fun updateHabit(
        habitId: String,
        request: HabitCompletionRequest
    ) {
        try {
            val token = sharedPrefs.getString("jwt", null) ?: throw Exception("User not authenticated")
            habitApi.markHabitAsCompleted("Bearer $token",habitId, request)
        } catch (e: HttpException) {
            Log.d("TAG", "updateHabit: $e")
        } catch (e: IOException) {

            Log.d("TAG", "updateHabit: io error $e")
        }
    }

    override suspend fun deleteHabit(habitId: String) {
        try {
            val token = sharedPrefs.getString("jwt", null) ?: throw Exception("User not authenticated")
            habitApi.deleteHabits("Bearer $token",habitId)
        } catch (e: HttpException) {
            Log.d("TAG", "deleteHabit: http error $e")

        } catch (e: IOException) {
            Log.d("TAG", "deleteHabit: io error $e")

        }
    }
}