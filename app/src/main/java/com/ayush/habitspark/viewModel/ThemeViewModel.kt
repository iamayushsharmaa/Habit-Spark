package com.ayush.habitspark.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class ThemeViewModel @Inject constructor() : ViewModel(){

    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme : StateFlow<Boolean> = _isDarkTheme

    fun toggleTheme(){
        _isDarkTheme.value = !_isDarkTheme.value
        print("toggle - $_isDarkTheme")
    }

    fun setDarkTheme(isDark: Boolean){
        _isDarkTheme.value = isDark
    }
}