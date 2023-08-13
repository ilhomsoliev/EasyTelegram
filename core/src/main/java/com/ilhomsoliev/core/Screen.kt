package com.ilhomsoliev.core

import androidx.navigation.NavBackStackEntry

sealed class Screen(val route: String) {
    object Home : Screen("Home")
    object Login : Screen("Login")
    object OnBoarding : Screen("OnBoarding")
    object Welcome : Screen("Welcome")
    object ChooseCountry : Screen("ChooseCountry")
    object Chat : Screen("chat/{chatId}") {
        fun buildRoute(chatId: Long): String = "chat/${chatId}"
        fun getChatId(entry: NavBackStackEntry): Long =
            entry.arguments!!.getString("chatId")?.toLong()
                ?: throw IllegalArgumentException("chatId argument missing.")
    }

    object CreateChat : Screen("CreateChat")
}