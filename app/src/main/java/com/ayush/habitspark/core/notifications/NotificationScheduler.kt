package com.ayush.habitspark.core.notifications

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit

object NotificationScheduler {

    private const val WORK_NAME = "daily_habit_reminder"
    private const val STREAK_WORK_NAME = "streak_risk_reminder"
    private const val STREAK_REMINDER_HOUR = 21
    private const val REMINDER_HOUR = 20           // 8 PM

    fun scheduleDailyReminder(context: Context) {
        val delay = calculateDelayUntilNextReminder(REMINDER_HOUR)

        val request = PeriodicWorkRequestBuilder<HabitReminderWorker>(1, TimeUnit.DAYS)
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

    fun scheduleStreakRiskReminder(context: Context) {
        val delay = calculateDelayUntilNextReminder(STREAK_REMINDER_HOUR)

        val request = PeriodicWorkRequestBuilder<StreakRiskWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            STREAK_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    fun cancelStreakRiskReminder(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(STREAK_WORK_NAME)
    }

    private fun calculateDelayUntilNextReminder(hour: Int): Long {
        val now = Calendar.getInstance()

        val target = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        if (target.before(now)) {
            target.add(Calendar.DAY_OF_MONTH, 1)
        }

        return target.timeInMillis - now.timeInMillis
    }
}