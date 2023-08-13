package com.ilhomsoliev.login.presentation.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.ilhomsoliev.core.Screen
import com.ilhomsoliev.login.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    vm: LoginViewModel,
    navController: NavController,
    countryCode: String?
) {
    val scope = rememberCoroutineScope()

    val uiState by vm.uiState
    val isLoading by vm.isLoading.collectAsState()
    val pickedCountry by vm.pickedCountry.collectAsState()
    val isAuthenticated = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit, block = {
        countryCode?.let {
            vm.loadCountry(countryCode)
        }
    })

    LoginContent(LoginState(
        uiState = uiState,
        isLoading = isLoading,
        pickedCountry = pickedCountry,
    ), object : LoginCallback {
        override fun insertPhoneNumber(value: String) {
            scope.launch { vm.insertPhoneNumber(value) }
        }

        override fun onBack() {
            // TODO
            navController.popBackStack()
        }

        override fun insertCode(value: String) {
            scope.launch { vm.insertCode(value) }
        }

        override fun insertPassword(value: String) {
            scope.launch { vm.insertPassword(value) }

        }

        override fun onSuccessfulAuthenticated() {
            if (isAuthenticated.value) return
            isAuthenticated.value = true
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }

        }

        override fun onChooseCountryClick() {
            navController.navigate(Screen.ChooseCountry.route)
        }

    })

}
