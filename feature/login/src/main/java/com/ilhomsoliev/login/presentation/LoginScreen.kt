package com.ilhomsoliev.login.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.ilhomsoliev.core.Screen
import com.ilhomsoliev.login.presentation.screen.WaitForCodeScreen
import com.ilhomsoliev.login.presentation.screen.WaitForNumberScreen
import com.ilhomsoliev.login.presentation.screen.WaitForPasswordScreen
import com.ilhomsoliev.login.viewmodel.LoginViewModel
import com.ilhomsoliev.login.viewmodel.UiState

@Composable
fun LoginScreen(
    vm: LoginViewModel,
    navController: NavController
) {
    val uiState by vm.uiState
    val isLoading by vm.isLoading.collectAsState()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Gray)) {
        when (uiState) {
            is UiState.InsertNumber -> {
                WaitForNumberScreen(isLoading) {
                    vm.insertPhoneNumber(it)
                }
            }

            is UiState.InsertCode -> WaitForCodeScreen(
                isLoading,
                (uiState as UiState.InsertCode).error
            ) {
                vm.insertCode(it)
            }

            is UiState.InsertPassword -> WaitForPasswordScreen(isLoading) {
                vm.insertPassword(it)
            }

            UiState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            UiState.Authenticated -> {
                navController.navigate(com.ilhomsoliev.core.Screen.Home.route) {
                    popUpTo(com.ilhomsoliev.core.Screen.Login.route) { inclusive = true }
                }
            }
        }
    }
}
