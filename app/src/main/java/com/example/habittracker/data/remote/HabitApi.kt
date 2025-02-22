package com.example.habittracker.data.remote

import com.example.habittracker.data.remote.request.HabitCompletionRequest
import com.example.habittracker.data.remote.request.HabitRequest
import com.example.habittracker.data.remote.response.HabitResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate

interface HabitApi {

    @POST("habits")
    suspend fun createHabit(
        @Body habitRequest: HabitRequest,
        @Header("Authorization") authToken: String
    )

    @GET("habits")
    suspend fun getHabitsByDate(
        @Header("Authorization") token: String,
        @Query("date") date: LocalDate
    ): List<HabitResponse>

    @PUT("habits/{habitId}")
    suspend fun markHabitAsCompleted(
        @Header("Authorization") token: String,
        @Path("habitId") habitId: String,
        @Body request: HabitCompletionRequest
    )

    @DELETE("habits/{habitId}")
    suspend fun deleteHabits(
        @Header("Authorization") token: String,
        @Path("habitId") habitId: String,
    )
}