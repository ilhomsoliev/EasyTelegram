package com.ilhomsoliev.login.presentation.welcome

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.ilhomsoliev.core.Screen

@Composable
fun WelcomeScreen(
    navController: NavController
) {

    WelcomeContent(object : WelcomeCallback {
        override fun onNextClick() {
            navController.navigate(Screen.Login.route)
        }

        override fun onGoToOnBoardingClick() {
            navController.navigate(Screen.OnBoarding.route)
        }
    })
}