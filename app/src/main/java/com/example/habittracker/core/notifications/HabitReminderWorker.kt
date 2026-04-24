package com.example.habittracker.core.notifications

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.habittracker.data.models.Resource
import com.example.habittracker.data.models.isCompletedOn
import com.example.habittracker.data.repository.HabitsRepository
import com.example.habittracker.utils.getTodayTimestamp
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class HabitReminderWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: HabitsRepository
) : CoroutineWorker(context, workerParams) {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override suspend fun doWork(): Result {
        return try {
            val today = getTodayTimestamp()

            val habitsResource = repository.getHabits().first()
            val pendingCount = when (habitsResource) {
                is Resource.Success -> {
                    habitsResource.data.count { habit ->
                        habit.isActive &&
                                habit.startDate <= today &&
                                !habit.isCompletedOn(today)
                    }
                }

                else -> 0
            }

            NotificationHelper.showDailyReminder(context, pendingCount)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}