package com.ilhomsoliev.home.presentation.chats

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.ilhomsoliev.core.Screen
import com.ilhomsoliev.home.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    vm: HomeViewModel,
    navController: NavController,
) {
    val chats = vm.chats.collectAsLazyPagingItems()
    val currentUser by vm.user.collectAsState()

    HomeContent(
        state = HomeState(
            currentUser = currentUser,
            isLoading = false,
            downloadManager = vm.downloadManager,
            chats = chats
        ),
        callback = object : HomeCallback {
            override fun onChatClick(id: Long) {
                navController.navigate(Screen.Chat.buildRoute(id))
            }

            override fun onSearchClick() {
                // TODO
            }

            override fun onNewMessageClick() {
                navController.navigate(Screen.NewMessage.route)
            }
        })

}

