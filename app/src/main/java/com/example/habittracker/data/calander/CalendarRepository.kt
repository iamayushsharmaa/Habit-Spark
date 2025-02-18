package com.example.habittracker.data.calander

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class CalendarRepository {

    private val currentDate = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault())

    private fun getStartOfWeek(offset: Int): Calendar {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.WEEK_OF_YEAR, offset)
        calendar.firstDayOfWeek = Calendar.MONDAY // Set the first day of the week
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        return calendar
    }

    fun getWeekDates(offset: Int): List<CalendarDay> {
        val startOfWeek = getStartOfWeek(offset)
        val weekDates = mutableListOf<CalendarDay>()

        val dateFormat = SimpleDateFormat("EEE", Locale.getDefault())

        for (i in 0 until 7) {
            val date = startOfWeek.clone() as Calendar
            date.add(Calendar.DAY_OF_YEAR, i)

            val day = dateFormat.format(date.time)
            val dayOfMonth = date.get(Calendar.DAY_OF_MONTH).toString()
            val isCurrentDate = date.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR) &&
                    date.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH) &&
                    date.get(Calendar.DAY_OF_MONTH) == currentDate.get(Calendar.DAY_OF_MONTH)

            Log.d("TAG", "current date $currentDate")
            weekDates.add(CalendarDay(day, dayOfMonth, isCurrentDate))
        }

        return weekDates
    }
}