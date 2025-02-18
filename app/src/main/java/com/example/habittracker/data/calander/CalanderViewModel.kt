package com.example.habittracker.data.calander

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

@HiltViewModel
class CalanderViewModel @Inject constructor(
    private val repository: CalendarRepository
): ViewModel() {

    private val _weekOffset = MutableStateFlow(0)
    val weekOffset: StateFlow<Int> get() = _weekOffset

    private val _weekDates = MutableStateFlow<List<CalendarDay>>(emptyList())
    val weekDates: StateFlow<List<CalendarDay>> get() = _weekDates

    init {
        loadWeekDates(0)
    }

    fun loadWeekDates(offset: Int) {
        viewModelScope.launch {
            _weekOffset.value = offset
            _weekDates.value = repository.getWeekDates(offset)
        }
    }

    fun updateWeekOffset(newOffset: Int) {
        loadWeekDates(newOffset)
    }
}