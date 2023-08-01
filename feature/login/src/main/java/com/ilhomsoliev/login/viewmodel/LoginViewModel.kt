package com.ilhomsoliev.login.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilhomsoliev.data.TelegramClient
import com.ilhomsoliev.data.auth.Authentication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LoginViewModel(
    private val client: TelegramClient
) : ViewModel() {

    private val _uiState = mutableStateOf<UiState>(UiState.Loading)
    val uiState: State<UiState> get() = _uiState

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        client.authState.onEach {
            when (it) {
                Authentication.UNAUTHENTICATED, Authentication.UNKNOWN -> {
                    _uiState.value = UiState.Loading
                }

                Authentication.WAIT_FOR_NUMBER -> {
                    _uiState.value = UiState.InsertNumber()
                }

                Authentication.WAIT_FOR_CODE -> {
                    _uiState.value = UiState.InsertCode()
                }
                Authentication.INCORRECT_CODE -> {
                    _uiState.value = UiState.InsertCode("Incorrect code")
                }
                Authentication.WAIT_FOR_PASSWORD -> {
                    _uiState.value = UiState.InsertPassword()
                }

                Authentication.AUTHENTICATED -> {
                    _uiState.value = UiState.Authenticated
                }

            }
            viewModelScope.launch {
                _isLoading.emit(false)
            }
        }.launchIn(viewModelScope)
    }

    fun insertPhoneNumber(number: String) {
        //_uiState.value = UiState.Loading
        viewModelScope.launch {
            _isLoading.emit(true)
        }
        client.insertPhoneNumber(number)
    }

    fun insertCode(code: String) {
        //_uiState.value = UiState.Loading
        viewModelScope.launch {
            _isLoading.emit(true)
        }
        client.insertCode(code)
    }

    fun insertPassword(password: String) {
        //_uiState.value = UiState.Loading
        viewModelScope.launch {
            _isLoading.emit(true)
        }
        client.insertPassword(password)
    }

}

sealed class UiState {
    object Loading : UiState()
    data class InsertNumber(val previousError: Throwable? = null) : UiState()
    data class InsertCode(val error: String? = null) : UiState()
    data class InsertPassword(val previousError: Throwable? = null) : UiState()
    object Authenticated : UiState()
}