package com.example.habittracker.viewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.repository.FirestoreRepository.FirestoreRepository
import com.example.habittracker.data.HabitData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirestoreViewModel @Inject constructor(
    private val repository: FirestoreRepository
) :ViewModel(){

    private val _habitsData = MutableStateFlow<List<HabitData>>(emptyList())
    val habitsData: StateFlow<List<HabitData>> get() = _habitsData

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun addHabits(habitData: HabitData){
        viewModelScope.launch {
            repository.addDataToFirestore(habitData)
            Log.d("addindb", "addHabits: habit added")
            getHabitData()
        }
    }

    fun getHabitData() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val habitsDataVM = repository.getDataFromFirestore()
                _habitsData.value = habitsDataVM
            } catch (e: Exception) {
               Log.e("FirestoreViewModel", "Error fetching habits: ${e.message}")
                _habitsData.value = emptyList()
            } finally {
                _loading.value = false
            }
        }
    }
}