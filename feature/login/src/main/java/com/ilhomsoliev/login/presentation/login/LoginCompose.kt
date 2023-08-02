package com.ilhomsoliev.login.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ilhomsoliev.login.presentation.login.screen.WaitForCodeScreen
import com.ilhomsoliev.login.presentation.login.screen.WaitForNumberScreen
import com.ilhomsoliev.login.presentation.login.screen.WaitForPasswordScreen
import com.ilhomsoliev.login.viewmodel.UiState

data class LoginState(
    val uiState: UiState,
    val isLoading: Boolean,
)

interface LoginCallback {
    fun insertPhoneNumber(value: String)
    fun insertCode(value: String)
    fun insertPassword(value: String)
    fun onSuccessfulAuthenticated()
    fun onChooseCountryClick()
}

@Composable
fun LoginContent(
    state: LoginState,
    callback: LoginCallback,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        when (state.uiState) {
            is UiState.InsertNumber -> {
                WaitForNumberScreen(state.isLoading, onChooseCountryClick = {
                    callback.onChooseCountryClick()
                }, onNumberEnter = {
                    callback.insertPhoneNumber(it)
                })
            }

            is UiState.InsertCode -> WaitForCodeScreen(
                isLoading = state.isLoading,
                error = (state.uiState).error
            ) {
                callback.insertCode(it)
            }

            is UiState.InsertPassword -> WaitForPasswordScreen(state.isLoading) {
                callback.insertPassword(it)
            }

            UiState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            UiState.Authenticated -> {
                callback.onSuccessfulAuthenticated()
            }
        }
    }
}