package com.ilhomsoliev.login.presentation.onboarding

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun OnBoardingScreen(navController: NavController) {
    OnBoardingContent(0, object : OnBoardingCallback{
        override fun onNextClick() {
            TODO("Not yet implemented")
        }

        override fun onBackClick() {
            TODO("Not yet implemented")
        }

        override fun onPreviousClick() {
            TODO("Not yet implemented")
        }

    })
}