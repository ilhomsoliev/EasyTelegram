package com.ilhomsoliev.home.presentation

import androidx.compose.runtime.Composable
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

    HomeContent(
        state = HomeState(isLoading = false, client = vm.client, chats = chats),
        callback = object : HomeCallback {
            override fun onChatClick(id: Long) {
                navController.navigate(Screen.Chat.buildRoute(id))
            }

            override fun onSearchClick() {
                // TODO
            }
        })

}

