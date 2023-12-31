package com.ilhomsoliev.login.presentation.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import com.ilhomsoliev.login.presentation.login.screens.WaitForCodeCallback
import com.ilhomsoliev.login.presentation.login.screens.WaitForCodeScreen
import com.ilhomsoliev.login.presentation.login.screens.WaitForCodeState
import com.ilhomsoliev.login.presentation.login.screens.WaitForNumberCallback
import com.ilhomsoliev.login.presentation.login.screens.WaitForNumberScreen
import com.ilhomsoliev.login.presentation.login.screens.WaitForNumberState
import com.ilhomsoliev.login.presentation.login.screens.WaitForPasswordCallback
import com.ilhomsoliev.login.presentation.login.screens.WaitForPasswordScreen
import com.ilhomsoliev.login.presentation.login.screens.WaitForPasswordState
import com.ilhomsoliev.login.viewmodel.UiState
import com.ilhomsoliev.shared.country.Country

data class LoginState(
    // General
    val uiState: UiState,
    val isLoading: Boolean,
    // Phone Number
    val pickedCountry: Country? = Country(),
    val phoneNumber: String = "",
    // Code
    val code: String,
    val focuses: List<FocusRequester>,
    val sec: Int,
    // Password
    val password: String,
)

interface LoginCallback {
    // General
    fun onBack()

    // Phone Number
    fun onPhoneNumberChange(value: String)
    fun onChooseCountryClick()
    fun onNextPhoneNumber()

    // Code
    fun firstRequest()
    fun onCodeChange(index: Int, number: String)
    fun insertCode(value: String)

    // Password
    fun onPasswordChange(value: String)
    fun onSendPasswordToCheck()
    fun onSuccessfulAuthenticated()
}

@Composable
fun LoginContent(
    state: LoginState,
    callback: LoginCallback,
) {
    val isPhoneNumberButtonActive =
        state.phoneNumber.isNotEmpty() && state.pickedCountry != null

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (state.uiState) {
            is UiState.InsertNumber -> {
                WaitForNumberScreen(
                    state = WaitForNumberState(
                        phoneNumber = state.phoneNumber,
                        pickedCountry = state.pickedCountry,
                        isLoading = state.isLoading,
                        isNextActive = isPhoneNumberButtonActive,
                    ),
                    callback = object : WaitForNumberCallback {
                        override fun onNewNumberEnter(number: String) {
                            callback.onPhoneNumberChange(number)
                        }

                        override fun onChooseCountryClick() {
                            callback.onChooseCountryClick()

                        }

                        override fun onBack() {
                            callback.onBack()

                        }

                        override fun onNext() {
                            callback.onNextPhoneNumber()
                        }
                    })
            }

            is UiState.InsertCode -> WaitForCodeScreen(
                WaitForCodeState(
                    code = state.code,
                    focuses = state.focuses,
                    sec = state.sec,
                    isLoading = state.isLoading,
                    country = state.pickedCountry,
                    phoneNumber = state.uiState.data.phoneNumber ?: state.phoneNumber,
                ),
                object : WaitForCodeCallback {
                    override fun onCodeChange(index: Int, number: String) {
                        callback.onCodeChange(index, number)
                    }

                    override fun onBack() {
                        callback.onBack()
                    }

                    override fun firstRequest() {
                        callback.firstRequest()
                    }
                }
            )

            is UiState.InsertPassword -> WaitForPasswordScreen(
                WaitForPasswordState(
                    state.password,
                    state.isLoading
                ),
                object : WaitForPasswordCallback {
                    override fun onPasswordChange(value: String) {
                        callback.onPasswordChange(value)
                    }

                    override fun onPasswordCheck() {
                        callback.onSendPasswordToCheck()
                    }

                    override fun onBack() {
                        callback.onBack()
                    }

                    override fun onNext() {
                        callback.onSendPasswordToCheck()
                    }

                }
            )

            UiState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            UiState.Authenticated -> {
                callback.onSuccessfulAuthenticated()
            }

            else -> {}
        }
    }
}