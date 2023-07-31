package com.ilhomsoliev.easytelegram.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ilhomsoliev.easytelegram.app.navigation.Screen
import com.ilhomsoliev.easytelegram.feature.auth.presentation.LoginScreen
import com.ilhomsoliev.easytelegram.feature.chat.presentation.ChatScreen
import com.ilhomsoliev.easytelegram.feature.home.presentation.HomeContainer
import org.koin.androidx.compose.koinViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screen.Home.route) {
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
        /*composable(Screen.CreateChat.route) {
            CreateChatScreen(
                navigateUp = navController::navigateUp,
                viewModel = hiltNavGraphViewModel(it)
            )
        }*/
        composable(Screen.Login.route) {
            LoginScreen(vm = koinViewModel(), navController = navController)
        }
    }
}