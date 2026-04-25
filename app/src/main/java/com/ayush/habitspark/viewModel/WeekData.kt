package com.ayush.habitspark.viewModel

import java.time.LocalDate

data class WeekData(
    val weekDates: List<LocalDate>,
    val monthName: String
)