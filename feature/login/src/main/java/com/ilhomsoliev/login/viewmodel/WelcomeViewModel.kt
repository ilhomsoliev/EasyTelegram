package com.ilhomsoliev.login.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilhomsoliev.auth.AuthRepository
import com.ilhomsoliev.tgcore.Authentication
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class WelcomeViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {
    // General
    private val _uiState = mutableStateOf<UiState>(UiState.Loading)
    val uiState: State<UiState> get() = _uiState

    init {
        authRepository.authState.onEach {
            when (it) {
                Authentication.UNAUTHENTICATED, Authentication.UNKNOWN -> {
                    _uiState.value = UiState.Loading
                }

                Authentication.WAIT_FOR_NUMBER -> {
                    _uiState.value = UiState.InsertNumber()
                }

                is Authentication.WAIT_FOR_CODE -> {
                    _uiState.value = UiState.InsertCode(it)
                }

                is Authentication.WAIT_FOR_PASSWORD -> {
                    _uiState.value = UiState.InsertPassword()
                }

                Authentication.AUTHENTICATED -> {
                    _uiState.value = UiState.Authenticated
                }

            }
        }.launchIn(viewModelScope)
    }
}