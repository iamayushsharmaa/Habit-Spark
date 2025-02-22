package com.example.habittracker.data.calander

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CalendarViewModel : ViewModel() {
    private val _currentDate = MutableStateFlow(LocalDate.now())
    val currentDate: StateFlow<LocalDate> = _currentDate.asStateFlow()

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    fun getWeekDates(weekOffset: Int): WeekData {
        val initialSunday = _currentDate.value.minusDays(_currentDate.value.dayOfWeek.value % 7L)
        val weekStart = initialSunday.plusWeeks(weekOffset.toLong())

        val weekDates = List(7) { dayIndex ->
            weekStart.plusDays(dayIndex.toLong())
        }

        return WeekData(
            weekDates = weekDates,
            monthName = weekStart.format(DateTimeFormatter.ofPattern("MMMM yyyy"))
        )
    }

    fun onDateSelected(date: LocalDate) {
        _selectedDate.value = date
    }

    fun updateCurrentDate(date: LocalDate) {
        _currentDate.value = date
    }
}
