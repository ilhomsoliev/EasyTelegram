package com.ilhomsoliev.home.presentation.chats

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ilhomsoliev.home.viewmodel.HomeViewModel
import com.ilhomsoliev.home.viewmodel.UiState

@Composable
fun HomeContainer(
    vm: HomeViewModel,
    openWelcome: () -> Unit,
    openChat: (id: Long) -> Unit,
    openNewMessage: () -> Unit,
) {
    val uiState by vm.uiState

    when (uiState) {
        UiState.Loading -> {
            LoadingScreen(Modifier)
        }

        UiState.Loaded -> {
            HomeScreen(
                vm = vm,
                openChat = openChat,
                openNewMessage = openNewMessage,
            )
        }

        is UiState.Login -> {
            openWelcome()
        }
    }
}

@Composable
private fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}