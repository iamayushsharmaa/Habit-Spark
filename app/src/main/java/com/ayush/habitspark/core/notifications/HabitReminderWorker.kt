package com.ayush.habitspark.core.notifications

import android.annotation.SuppressLint
import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ayush.habitspark.data.models.Resource
import com.ayush.habitspark.data.models.isCompletedOn
import com.ayush.habitspark.data.models.isScheduledFor
import com.ayush.habitspark.data.repository.HabitsRepository
import com.ayush.habitspark.utils.getTodayTimestamp
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class HabitReminderWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: HabitsRepository
) : CoroutineWorker(context, workerParams) {

    @SuppressLint("MissingPermission")
    override suspend fun doWork(): Result {
        return try {
            val today = getTodayTimestamp()

            val habitsResource = repository.getHabits().first()
            val pendingCount = when (habitsResource) {
                is Resource.Success -> {
                    habitsResource.data.count { habit ->
                        habit.isActive &&
                                habit.startDate <= today &&
                                !habit.isCompletedOn(today) &&
                                habit.isScheduledFor(today)
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