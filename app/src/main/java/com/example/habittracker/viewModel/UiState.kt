package com.example.habittracker.viewModel

data class UiState<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val errorMessage: String? = null
)