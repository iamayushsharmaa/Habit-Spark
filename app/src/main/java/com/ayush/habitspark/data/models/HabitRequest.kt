package com.ayush.habitspark.data.models

data class HabitRequest(
    val name: String,
    val icon : String,
    val iconBackground: String,
    val description: String,
    val goal : Goal,
    val frequency: Frequency,
    val startDate: Long,
    val isActive: Boolean
)

enum class Frequency {
    EVERYDAY,
    ALTERNATE,
    WEEKLY,
    MONTHLY
}

data class Goal(
    val value: String,
    val unit: String
)

fun Frequency.toDisplayString(): String {
    return when (this) {
        Frequency.EVERYDAY -> "Everyday"
        Frequency.ALTERNATE -> "Alternative"
        Frequency.WEEKLY -> "Weekly"
        Frequency.MONTHLY -> "Monthly"
    }
}