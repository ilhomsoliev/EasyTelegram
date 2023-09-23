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

    // Phone Number
    val pickedCountry by vm.pickedCountry.collectAsState()
    val phoneNumber by vm.phoneNumber.collectAsState()

    // Code
    val code by vm.code.collectAsState()
    val timer by vm.timer.collectAsState()
    val focuses by vm.focuses.collectAsState()

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
        phoneNumber = phoneNumber,
        code = code,
        focuses = focuses,
        sec = timer,
    ), object : LoginCallback {
        override fun onBack() {
            // TODO
            navController.popBackStack()
        }

        override fun firstRequest() {
            vm.firstFocus()
        }

        override fun onPhoneNumberChange(value: String) {
            scope.launch { vm.onPhoneNumberChange(value) }
        }

        override fun onCodeChange(index: Int, number: String) {
            if (isLoading) return
            scope.launch {
                vm.onCodeChange(index, number)
                if (vm.code.value.length == vm.codeLength.value) try {
                    vm.insertCode(vm.code.value)

                } catch (e: Exception) {
                    e.stackTraceToString()

                }
            }
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

        override fun onNextPhoneNumber() {
            scope.launch { vm.onPhoneNumberValidate() }
        }

    })

}
