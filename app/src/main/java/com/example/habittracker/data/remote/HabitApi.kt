package com.example.habittracker.data.remote

import com.example.habittracker.data.remote.request.HabitRequest
import com.example.habittracker.data.remote.response.HabitResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface HabitApi {

    @POST("habits")
    suspend fun createHabit(@Body habit: HabitRequest)

    @GET("habits")
    suspend fun getHabits(
        @Query("userId") userId: String
    ): List<HabitResponse>

    @PUT("habits")
    suspend fun updateHabits(
        @Query("userId") userId: String,
        @Body habit: HabitRequest
    ): HabitResponse

    @DELETE("habits")
    suspend fun deleteHabits(
        @Query("userId") userId: String,
        @Body habit: HabitRequest
    )
}