package com.ilhomsoliev.login.presentation.welcome

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import com.ilhomsoliev.login.viewmodel.UiState
import com.ilhomsoliev.login.viewmodel.WelcomeViewModel

@Composable
fun WelcomeScreen(
    vm: WelcomeViewModel,
    openOnBoarding: () -> Unit,
    openLogin: () -> Unit,
) {

    val uiState by vm.uiState

    if (uiState !is UiState.InsertNumber) {
        openLogin()
    }

    WelcomeContent(object : WelcomeCallback {
        override fun onNextClick() {
            openLogin()
        }

        override fun onGoToOnBoardingClick() {
            openOnBoarding()
        }
    })
}