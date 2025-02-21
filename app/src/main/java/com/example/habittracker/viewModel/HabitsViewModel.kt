package com.example.habittracker.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.data.remote.HabitsRepository
import com.example.habittracker.data.remote.request.HabitCompletionRequest
import com.example.habittracker.data.remote.request.HabitRequest
import com.example.habittracker.data.remote.response.HabitResponse
import com.example.habittracker.data.remote.response.Resource
import com.google.api.ResourceProto.resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HabitsViewModel @Inject constructor(
    private val habitsRepository: HabitsRepository
) : ViewModel() {

    private val _habitsUiState = MutableStateFlow<UiState<List<HabitResponse>>>(UiState())
    val habitsUiState: StateFlow<UiState<List<HabitResponse>>> = _habitsUiState


    fun createHabit(habit: HabitRequest){
        viewModelScope.launch {
            habitsRepository.createHabit(habit)
        }
    }

    fun getHabitsByDate(date: LocalDate) {
        viewModelScope.launch {
            habitsRepository.getHabitsByDate(date)
                .map { resource ->
                    when (resource) {
                        is Resource.Loading -> UiState(isLoading = true)
                        is Resource.Success -> UiState(data = resource.data)
                        is Resource.Error -> UiState(errorMessage = resource.message)
                    }
                }
                .collect { uiState ->
                    _habitsUiState.value = uiState
                }
        }
    }
    fun refreshHabits() {
        viewModelScope.launch {
            habitsRepository.getHabitsByDate(LocalDate.now())
                .map { resource ->
                    when (resource) {
                        is Resource.Loading -> UiState(isLoading = true)
                        is Resource.Success -> UiState(data = resource.data)
                        is Resource.Error -> UiState(errorMessage = resource.message)
                    }
                }
                .collect { uiState ->
                    _habitsUiState.value = uiState
                }
        }
    }

    fun markHabitAsCompleted(habitId: String, habitCompletionRequest: HabitCompletionRequest) {
        viewModelScope.launch {
            habitsRepository.updateHabit(habitId, habitCompletionRequest)
        }
    }

    fun deletHabit(habitId: String) {
        viewModelScope.launch {
            habitsRepository.deleteHabit(habitId)
        }
    }

}