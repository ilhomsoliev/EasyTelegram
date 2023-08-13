package com.ilhomsoliev.login.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilhomsoliev.auth.AuthRepository
import com.ilhomsoliev.shared.country.Country
import com.ilhomsoliev.shared.country.CountryManager
import com.ilhomsoliev.tgcore.Authentication.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val countryManager: CountryManager,
) : ViewModel() {

    private val _uiState = mutableStateOf<UiState>(UiState.Loading)
    val uiState: State<UiState> get() = _uiState

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _pickedCountry = MutableStateFlow<Country?>(null)
    val pickedCountry = _pickedCountry.asStateFlow()

    init {
        authRepository.authState.onEach {
            when (it) {
                UNAUTHENTICATED, UNKNOWN -> {
                    _uiState.value = UiState.Loading
                }

                WAIT_FOR_NUMBER -> {
                    _uiState.value = UiState.InsertNumber()
                }

                WAIT_FOR_CODE -> {
                    _uiState.value = UiState.InsertCode()
                }

                INCORRECT_CODE -> {
                    _uiState.value = UiState.InsertCode("Incorrect code")
                }

                WAIT_FOR_PASSWORD -> {
                    _uiState.value = UiState.InsertPassword()
                }

                AUTHENTICATED -> {
                    _uiState.value = UiState.Authenticated
                }

            }
            viewModelScope.launch {
                _isLoading.emit(false)
            }
        }.launchIn(viewModelScope)
    }

    suspend fun loadCountry(countryCode: String) {
        countryManager.getCountryFromCountryCode(countryCode)?.let {
            _pickedCountry.emit(it)
        }
    }

    fun insertPhoneNumber(number: String) {
        viewModelScope.launch {
            _isLoading.emit(true)
        }
        authRepository.insertPhoneNumber(
            "+992927266950"
            //number TODO
        )
    }

    fun insertCode(code: String) {
        viewModelScope.launch {
            _isLoading.emit(true)
        }
        authRepository.insertCode(code)
    }

    fun insertPassword(password: String) {
        viewModelScope.launch {
            _isLoading.emit(true)
        }
        authRepository.insertPassword(password)
    }

}

sealed class UiState {
    object Loading : UiState()
    data class InsertNumber(val previousError: Throwable? = null) : UiState()
    data class InsertCode(val error: String? = null) : UiState()
    data class InsertPassword(val previousError: Throwable? = null) : UiState()
    object Authenticated : UiState()
}