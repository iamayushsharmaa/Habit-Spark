package com.example.habittracker.data.remote

import com.example.habittracker.data.remote.request.HabitCompletionRequest
import com.example.habittracker.data.remote.request.HabitRequest
import com.example.habittracker.data.remote.response.HabitResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate

interface HabitApi {

    @POST("habits")
    suspend fun createHabit(
        @Body habit: HabitRequest
    )

    @GET("habits")
    suspend fun getHabitsByDate(
        @Query("userId") userId: String,
        @Query("date") date: LocalDate
    ): List<HabitResponse>

    @PUT("habits/{habitId}")
    suspend fun markHabitAsCompleted(
        @Path("habitId") habitId: String,
        @Body request: HabitCompletionRequest
    )

    @DELETE("habits/{habitId}")
    suspend fun deleteHabits(
        @Path("habitId") habitId: String,
    )
}