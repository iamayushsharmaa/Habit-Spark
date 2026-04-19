package com.example.habittracker.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.data.repository.HabitsRepository
import com.example.habittracker.data.models.HabitRequest
import com.example.habittracker.data.models.HabitResponse
import com.example.habittracker.data.models.Resource
import com.example.habittracker.utils.getTodayTimestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitsViewModel @Inject constructor(
    private val repository: HabitsRepository
) : ViewModel() {

    private var _uiState = MutableStateFlow(HabitsUiState())
    val uiState: StateFlow<HabitsUiState> = _uiState

    init {
        observeHabits()
    }


    private fun observeHabits() {
        viewModelScope.launch {
            repository.getHabits().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = true,
                            error = null
                        )
                    }

                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            habits = resource.data,
                            error = null
                        )
                    }

                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = resource.message
                        )
                    }
                }
            }
        }
    }

    fun createHabit(habit: HabitRequest) {
        viewModelScope.launch {
            try {
                repository.createHabit(habit)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message
                )
            }
        }
    }

    fun toggleHabit(habitId: String) {
        viewModelScope.launch {
            try {
                val today = getTodayTimestamp()
                repository.toggleHabitCompletion(habitId, today)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message
                )
            }
        }
    }

    fun deleteHabit(habitId: String) {
        viewModelScope.launch {
            try {
                repository.deleteHabit(habitId)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message
                )
            }
        }
    }


    fun getTodaysHabit(): List<HabitResponse> {
        val today = getTodayTimestamp()

        return _uiState.value.habits.filter {
            it.startDate <= today && it.isActive
        }
    }


}