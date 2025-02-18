package com.example.habittracker.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class GoalRequest (
    val unit: String,
    val value: Int,
    val periodicity: String
)