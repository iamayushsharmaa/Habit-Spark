package com.ayush.habitspark.core.notifications

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ayush.habitspark.data.models.Resource
import com.ayush.habitspark.data.models.isCompletedOn
import com.ayush.habitspark.data.models.isScheduledFor
import com.ayush.habitspark.data.repository.HabitsRepository
import com.ayush.habitspark.utils.StreakUtils
import com.ayush.habitspark.utils.getTodayTimestamp
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class StreakRiskWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: HabitsRepository
) : CoroutineWorker(context, workerParams) {

    @SuppressLint("MissingPermission")
    override suspend fun doWork(): Result {

        return try {
            val today = getTodayTimestamp()

            val habitResource = repository.getHabits().first()

            if (habitResource !is Resource.Success) return Result.success()

            val habits = habitResource.data
            val activeHabits = habits.filter { it.isActive && it.startDate <= today }

            if (activeHabits.isEmpty()) return Result.success()


            val streakAtRisk = activeHabits.any { habit ->
                val streak = StreakUtils.calculateHabitStreak(habit.completionHistory)
                val completedToday = habit.isCompletedOn(today)
                streak > 0 && !completedToday
            }

            val pendingCount =
                activeHabits.count { it.isScheduledFor(today) && !it.isCompletedOn(today) }

            if (streakAtRisk && pendingCount > 0) {
                NotificationHelper.showStreakRiskReminder(context, pendingCount)
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

}