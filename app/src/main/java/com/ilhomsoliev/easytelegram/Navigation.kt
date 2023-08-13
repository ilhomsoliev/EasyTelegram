package com.ilhomsoliev.easytelegram

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ilhomsoliev.chat.presentation.ChatScreen
import com.ilhomsoliev.core.Screen
import com.ilhomsoliev.home.presentation.HomeContainer
import com.ilhomsoliev.login.presentation.chooseCountry.ChooseCountryScreen
import com.ilhomsoliev.login.presentation.login.LoginScreen
import com.ilhomsoliev.login.presentation.onboarding.OnBoardingScreen
import com.ilhomsoliev.login.presentation.welcome.WelcomeScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        composable(Screen.Home.route) {
            HomeContainer(navController = navController, vm = koinViewModel())
        }

        composable(Screen.Chat.route) {
            val chatId = Screen.Chat.getChatId(it)
            ChatScreen(
                chatId = chatId,
                navController = navController,
                vm = koinViewModel()
            )
        }

        composable(Screen.Login.route) {
            val countryCode = it.savedStateHandle.get<String>("country_code")
            it.savedStateHandle.remove<Boolean>("country_code")
            LoginScreen(vm = koinViewModel(), navController = navController, countryCode)
        }

        composable(Screen.Welcome.route) {
            WelcomeScreen(navController = navController)
        }

        composable(Screen.OnBoarding.route) {
            OnBoardingScreen(navController = navController)
        }


        composable(Screen.ChooseCountry.route) {
            ChooseCountryScreen(vm = koinViewModel(), navController = navController)
        }

    }
}