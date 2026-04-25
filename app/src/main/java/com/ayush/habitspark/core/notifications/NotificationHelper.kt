package com.ayush.habitspark.core.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ayush.habitspark.R
import com.ayush.habitspark.view.navigation.MainActivity

object NotificationHelper {
    private const val CHANNEL_ID = "habit_daily_reminder"
    private const val CHANNEL_NAME = "Daily Habit Reminder"
    private const val NOTIFICATION_ID = 1001
    private const val STREAK_NOTIFICATION_ID = 1002

    fun createNotificationChannel(context: Context) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Reminds you to complete your daily habits"
            enableVibration(true)
        }

        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }


    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showDailyReminder(context: Context, pendingHabitCount: Int) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val message = if (pendingHabitCount > 0)
            "You have $pendingHabitCount habit${if (pendingHabitCount > 1) "s" else ""} left to complete today!"
        else
            "Don't forget to complete your habits today!"

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.fitness)
            .setContentTitle("Evening Check-in 🔥")
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()


        try {
            NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
        } catch (e: Exception) {
            // permission not granted
        }

    }


    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showStreakRiskReminder(context: Context, pendingCount: Int) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 1, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val message =
            "You have $pendingCount habit${if (pendingCount > 1) "s" else ""} " +
                    "left to complete. Don't break your streak!"

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.fire_icon)
            .setContentTitle("⚠️ Streak at Risk!")
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        try {
            NotificationManagerCompat.from(context).notify(STREAK_NOTIFICATION_ID, notification)
        } catch (e: Exception) {
            //permission not granted
        }
    }

}