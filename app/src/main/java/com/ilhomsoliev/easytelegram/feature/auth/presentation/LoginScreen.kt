package com.ilhomsoliev.easytelegram.feature.auth.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ilhomsoliev.easytelegram.app.navigation.Screen
import com.ilhomsoliev.easytelegram.feature.auth.presentation.screen.WaitForCodeScreen
import com.ilhomsoliev.easytelegram.feature.auth.presentation.screen.WaitForNumberScreen
import com.ilhomsoliev.easytelegram.feature.auth.presentation.screen.WaitForPasswordScreen
import com.ilhomsoliev.easytelegram.feature.auth.viewmodel.LoginViewModel
import com.ilhomsoliev.easytelegram.feature.auth.viewmodel.UiState

@Composable
fun LoginScreen(
    vm: LoginViewModel,
    navController: NavController
) {
    val uiState by vm.uiState
    val isLoading by vm.isLoading.collectAsState()

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
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        }
    }

}
