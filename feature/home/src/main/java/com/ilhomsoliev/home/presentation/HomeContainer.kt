package com.ilhomsoliev.home.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ilhomsoliev.core.Screen
import com.ilhomsoliev.home.viewmodel.HomeViewModel
import com.ilhomsoliev.home.viewmodel.UiState

@Composable
fun HomeContainer(
    vm: HomeViewModel,
    navController: NavController,
) {
    val uiState by vm.uiState

    when (uiState) {
        UiState.Loading -> {
            LoadingScreen(Modifier)
        }

        UiState.Loaded -> {
            HomeScreen(vm = vm, navController = navController)
        }

        UiState.Login -> {
            navController.navigate(Screen.Welcome.route) {
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