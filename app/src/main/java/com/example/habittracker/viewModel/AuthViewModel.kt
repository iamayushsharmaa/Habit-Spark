package com.example.habittracker.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.common.ui_states.AuthState
import com.example.habittracker.data.auth.AuthRepository
import com.example.habittracker.data.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    var state by mutableStateOf<AuthState>(AuthState.Idle)
        private set

    fun signUp(name: String, email: String, password: String) {
        viewModelScope.launch {
            state = AuthState.Loading

            val result = repository.signUp(name, email, password)

            state = when (result) {
                is AuthResult.Success -> AuthState.Success
                is AuthResult.Error -> AuthState.Error(result.message)
            }
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            state = AuthState.Loading

            val result = repository.signIn(email, password)

            state = when (result) {
                is AuthResult.Success -> AuthState.Success
                is AuthResult.Error -> AuthState.Error(result.message)
            }
        }
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            state = AuthState.Loading

            val result = repository.signInWithGoogle(idToken)

            state = when (result) {
                is AuthResult.Success -> AuthState.Success
                is AuthResult.Error -> AuthState.Error(result.message)
            }
        }
    }

    fun getCurrentUser() {
        val userId = repository.getCurrentUserId()
        state = if (userId != null) {
            AuthState.Success
        } else {
            AuthState.Idle
        }
    }

    fun logout() {
        repository.logout()
        state = AuthState.Idle
    }
}