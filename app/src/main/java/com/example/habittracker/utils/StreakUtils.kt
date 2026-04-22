package com.example.habittracker.utils

import com.example.habittracker.data.models.HabitCompletion
import com.example.habittracker.data.models.HabitResponse
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

object StreakUtils {

    private fun Long.toLocalDate(): LocalDate =
        Instant.ofEpochMilli(this)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

    fun calculateHabitStreak(completionHistory: List<HabitCompletion>): Int {
        if (completionHistory.isEmpty()) return 0

        val completedDates = completionHistory
            .filter { it.isCompleted }
            .map { it.date.toLocalDate() }
            .sortedDescending()

        if (completedDates.isEmpty()) return 0

        val today = LocalDate.now()
        val yesterday = today.minusDays(1)

        if (completedDates.first() != today && completedDates.first() != yesterday) return 0;

        var streak = 1

        for (i in 0 until completedDates.size - 1) {
            val current = completedDates[i]
            val next = completedDates[i + 1]
            if (current.minusDays(1) == next) {
                streak++
            } else {
                break
            }
        }
        return streak
    }


    fun calculateGlobalStreak(habits: List<HabitResponse>): Int {

        if (habits.isEmpty()) return 0

        val today = LocalDate.now()
        val yesterday = today.minusDays(1)

        var streak = 0
        var checkDate = if (wasCompletedOn(habits, today)) today else yesterday

        if (!wasCompletedOn(habits, checkDate)) return 0

        while (wasCompletedOn(habits, checkDate)) {
            streak++
            checkDate = checkDate.minusDays(1)
        }
        return streak
    }

    private fun wasCompletedOn(
        habits: List<HabitResponse>,
        date: LocalDate
    ): Boolean {

        val dateTimeStamp = date
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        val activeHabits = habits.filter { it.startDate <= dateTimeStamp && it.isActive }

        if (activeHabits.isEmpty()) return false

        return activeHabits.all { habit ->
            habit.completionHistory.any { completion ->
                completion.date.toLocalDate() == date && completion.isCompleted

            }
        }
    }

    fun calculateLongestHabitStreak(completionHistory: List<HabitCompletion>): Int {
        if (completionHistory.isEmpty()) return 0

        val sorted = completionHistory
            .filter { it.isCompleted }
            .map { it.date.toLocalDate() }
            .sorted()

        if (sorted.isEmpty()) return 0

        var longest = 1
        var current = 1

        for (i in 1 until sorted.size) {
            if (sorted[i - 1].plusDays(1) == sorted[i]) {
                current++
                longest = maxOf(longest, current)
            } else {
                current = 1
            }
        }
        return longest
    }
}