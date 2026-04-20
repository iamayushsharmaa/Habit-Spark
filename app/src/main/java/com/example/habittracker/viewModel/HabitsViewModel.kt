package com.example.habittracker.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.data.models.HabitRequest
import com.example.habittracker.data.models.HabitResponse
import com.example.habittracker.data.models.Resource
import com.example.habittracker.data.repository.HabitsRepository
import com.example.habittracker.utils.StreakUtils
import com.example.habittracker.utils.getTodayTimestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitsViewModel @Inject constructor(
    private val repository: HabitsRepository
) : ViewModel() {

    private var _uiState = MutableStateFlow(HabitsUiState())
    val uiState: StateFlow<HabitsUiState> = _uiState

    private val _selectedDate = MutableStateFlow(getTodayTimestamp())
    val selectedDate: StateFlow<Long> = _selectedDate

    val globalStreak: StateFlow<Int> = _uiState
        .map { state ->
            StreakUtils.calculateGlobalStreak(state.habits)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    private val streakFlowCache = mutableMapOf<String, StateFlow<Int>>()
    private val longestStreakFlowCache = mutableMapOf<String, StateFlow<Int>>()


    fun habitStreakFlow(habitId: String): StateFlow<Int> =
        streakFlowCache.getOrPut(habitId) {
            _uiState
                .map { state ->
                    val habit = state.habits.find { it.habitId == habitId }
                    if (habit != null) StreakUtils.calculateHabitStreak(habit.completionHistory)
                    else 0
                }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = 0
                )
        }

    fun longestHabitStreakFlow(habitId: String): StateFlow<Int> =
        longestStreakFlowCache.getOrPut(habitId) {
            _uiState
                .map { state ->
                    val habit = state.habits.find { it.habitId == habitId }
                    if (habit != null) StreakUtils.calculateLongestHabitStreak(habit.completionHistory)
                    else 0
                }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = 0
                )
        }


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
                val selected = _selectedDate.value
                val today = getTodayTimestamp()

                if (selected != today) return@launch

                repository.toggleHabitCompletion(habitId, selected)
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


    fun getHabitsForSelectedDate(): List<HabitResponse> {
        val selected = _selectedDate.value

        return _uiState.value.habits.filter {
            it.startDate <= selected && it.isActive
        }
    }

    fun onDateSelected(date: Long) {
        _selectedDate.value = date
    }
}