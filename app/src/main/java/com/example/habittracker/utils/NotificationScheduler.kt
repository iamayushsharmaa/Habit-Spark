package com.example.habittracker.utils

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit

object NotificationScheduler {


    private const val WORK_NAME = "daily_habit_reminder"
    private const val REMINDER_HOUR = 20           // 8 PM
    private const val REMINDER_MINUTE = 0

    fun scheduleDailyReminder(context: Context) {
        val delay = calculateDelayUntilNextReminder()

        val request = PeriodicWorkRequestBuilder<HabitReminderWorker>(
            1, TimeUnit.DAYS
        )
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    fun cancelReminder(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)
    }

    private fun calculateDelayUntilNextReminder(): Long {
        val now = Calendar.getInstance()

        val target = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, REMINDER_HOUR)
            set(Calendar.MINUTE, REMINDER_MINUTE)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        if (target.before(now)) {
            target.add(Calendar.DAY_OF_MONTH, 1)
        }

        return target.timeInMillis - now.timeInMillis
    }
}