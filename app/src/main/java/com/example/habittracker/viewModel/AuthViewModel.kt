package com.example.habittracker.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.data.auth.AuthRepository
import com.example.habittracker.data.auth.AuthResult
import com.example.habittracker.view.auth.AuthState
import com.example.habittracker.view.auth.AuthUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {

    var state by mutableStateOf(AuthState())

    private val resultChannel = Channel<AuthResult<Unit>>()
    val authResult = resultChannel.receiveAsFlow()

    init {
        authenticate()
    }
    fun onEvent(event: AuthUiEvent){
        when(event){
            is AuthUiEvent.SignInUsernameChanged -> {
                state = state.copy(signinUsername = event.value)
            }
            is AuthUiEvent.SignInPasswordChanged ->{
                state = state.copy(signinPassword = event.value)
            }
            is AuthUiEvent.SignIn -> {
                signin()
            }
            is AuthUiEvent.SignUpUsernameChanged -> {
                state = state.copy(signupUsername = event.value)
            }
            is AuthUiEvent.SignUpPasswordChanged -> {
                state = state.copy(signupPassword = event.value)
            }
            is AuthUiEvent.SignUp -> {
                signup()
            }
        }
    }

    private fun signup(){
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.signUp(
                username = state.signupUsername,
                password = state.signupUsername
            )
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }

    private fun signin(){
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.signIn(
                username = state.signupUsername,
                password = state.signupUsername
            )
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }
    private fun authenticate(){
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.authenticate()
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }
}