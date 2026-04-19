package com.example.habittracker.viewModel

import com.example.habittracker.data.models.HabitResponse

data class HabitsUiState(
    val isLoading: Boolean = false,
    val habits: List<HabitResponse> = emptyList(),
    val error: String? = null
)