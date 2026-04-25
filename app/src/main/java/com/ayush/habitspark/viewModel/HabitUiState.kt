package com.ayush.habitspark.viewModel

import com.ayush.habitspark.data.models.HabitResponse

data class HabitsUiState(
    val isLoading: Boolean = false,
    val habits: List<HabitResponse> = emptyList(),
    val error: String? = null
)