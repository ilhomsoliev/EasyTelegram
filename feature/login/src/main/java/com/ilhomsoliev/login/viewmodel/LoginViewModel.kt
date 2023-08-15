package com.ilhomsoliev.login.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilhomsoliev.auth.AuthRepository
import com.ilhomsoliev.shared.country.Country
import com.ilhomsoliev.shared.country.CountryManager
import com.ilhomsoliev.tgcore.Authentication.AUTHENTICATED
import com.ilhomsoliev.tgcore.Authentication.INCORRECT_CODE
import com.ilhomsoliev.tgcore.Authentication.UNAUTHENTICATED
import com.ilhomsoliev.tgcore.Authentication.UNKNOWN
import com.ilhomsoliev.tgcore.Authentication.WAIT_FOR_CODE
import com.ilhomsoliev.tgcore.Authentication.WAIT_FOR_NUMBER
import com.ilhomsoliev.tgcore.Authentication.WAIT_FOR_PASSWORD
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val countryManager: CountryManager,
) : ViewModel() {

    // General
    private val _uiState = mutableStateOf<UiState>(UiState.Loading)
    val uiState: State<UiState> get() = _uiState

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    //Phone Number
    private val _pickedCountry = MutableStateFlow<Country?>(null)
    val pickedCountry = _pickedCountry.asStateFlow()

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber = _phoneNumber.asStateFlow()

    // Code
    private val _codeLength = MutableStateFlow(5)
    val codeLength = _codeLength.asStateFlow()

    private val _code = MutableStateFlow("")
    val code = _code.asStateFlow()

    private val _timer = MutableStateFlow(60)
    val timer = _timer.asStateFlow()

    private val _focuses = MutableStateFlow(lazy {
        val focuses = arrayListOf<FocusRequester>()
        repeat(codeLength.value) { focuses.add(FocusRequester()) }
        focuses
    }.value)
    val focuses = _focuses.asStateFlow()

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

    // Phone Number
    suspend fun loadCountry(countryCode: String) {
        countryManager.getCountryFromCountryCode(countryCode)?.let {
            _pickedCountry.emit(it)
        }
    }

    suspend fun onPhoneNumberChange(value: String) {
        _phoneNumber.emit(value)
    }

    fun onPhoneNumberValidate() {
        if (_phoneNumber.value.isEmpty()) return

        viewModelScope.launch {
            _isLoading.emit(true)
        }
        authRepository.insertPhoneNumber(
            "+992927266950"
            //number TODO
        )
    }

    // Code
    fun firstFocus() {
        focuses.value.first().requestFocus()
    }

    suspend fun onCodeClear() {
        _code.emit("")
        focuses.value[0].requestFocus()
    }

    suspend fun onCodeChange(index: Int, text: String) {
        if (_isLoading.value) return
        if (code.value.length <= codeLength.value) {
            if (text.length == codeLength.value) {
                _code.emit(text)
            } else if (text.length < 2) {
                if (text == "") {
                    _code.emit(
                        code.value.substring(
                            0, if (code.value.lastIndex == -1) 0 else code.value.lastIndex
                        )
                    )
                    if (index - 1 >= 0)
                        focuses.value[index - 1].requestFocus()
                } else {
                    _code.emit(code.value + text)
                    if (index + 1 < codeLength.value)
                        focuses.value[index + 1].requestFocus()
                }
            }
        } else _code.emit("")
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