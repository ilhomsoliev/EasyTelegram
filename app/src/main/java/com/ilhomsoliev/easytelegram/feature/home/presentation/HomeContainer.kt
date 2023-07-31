package com.ilhomsoliev.easytelegram.feature.home.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ilhomsoliev.easytelegram.app.navigation.Screen
import com.ilhomsoliev.easytelegram.feature.home.viewmodel.HomeViewModel
import com.ilhomsoliev.easytelegram.feature.home.viewmodel.UiState

@Composable
fun HomeContainer(
    vm: HomeViewModel,
    navController: NavController,
) {
    val uiState by vm.uiState
    when (uiState) {
        UiState.Loading -> {
            LoadingScreen(Modifier)
            Text(text = "Home Screen")
        }

        UiState.Loaded -> {
            HomeScreen(vm = vm, navController = navController)
        }

        UiState.Login -> {
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Home.route) { inclusive = true }
                launchSingleTop = true
            }
        }
    }
}

@Composable
private fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}