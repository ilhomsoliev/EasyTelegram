package com.ilhomsoliev.login.presentation.onboarding

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.ilhomsoliev.core.Screen

@Composable
fun OnBoardingScreen(navController: NavController) {
    OnBoardingContent(object : OnBoardingCallback {

        override fun onBackClick() {
            navController.popBackStack()
        }

        override fun onDone() {
            val options = navOptions {
                popUpTo(Screen.OnBoarding.route) { inclusive = true }
            }
            navController.navigate(Screen.Login.route, options)
        }
    })
}