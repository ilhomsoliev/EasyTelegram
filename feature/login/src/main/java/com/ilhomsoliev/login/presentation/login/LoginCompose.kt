package com.ilhomsoliev.login.presentation.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
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
import com.ilhomsoliev.login.presentation.login.screens.WaitForPasswordScreen
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
)

interface LoginCallback {
    fun onBack()
    fun firstRequest()
    fun onPhoneNumberChange(value: String)
    fun onCodeChange(index: Int, number: String)
    fun insertCode(value: String)
    fun insertPassword(value: String)
    fun onSuccessfulAuthenticated()
    fun onChooseCountryClick()
    fun onNextPhoneNumber()
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
                    phoneNumber = state.phoneNumber,
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

            is UiState.InsertPassword -> WaitForPasswordScreen(state.isLoading) {
                callback.insertPassword(it)
            }

            UiState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                    Text(text = "Here")
                }
            }

            UiState.Authenticated -> {
                callback.onSuccessfulAuthenticated()
            }

            else -> {}
        }
    }
}