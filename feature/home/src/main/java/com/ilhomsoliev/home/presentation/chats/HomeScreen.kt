package com.ilhomsoliev.home.presentation.chats

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.ilhomsoliev.core.Screen
import com.ilhomsoliev.home.viewmodel.HomeViewModel
import com.ilhomsoliev.tgcore.newUpdateFromTdApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    vm: HomeViewModel,
    navController: NavController,
) {
    val scope = rememberCoroutineScope()

    val chats by vm.chats.collectAsState()

    val currentUser by vm.user.collectAsState()

    LaunchedEffect(key1 = Unit) {
        vm.loadChats()
        while(true){
            delay(1000L)
            vm.updateChats()
        }
    }

    /*LaunchedEffect(key1 = newUpdateFromTdApi.value, block = {
        if (newUpdateFromTdApi.value == null) return@LaunchedEffect
        newUpdateFromTdApi.value = null
    })*/

    HomeContent(
        state = HomeState(
            currentUser = currentUser,
            isLoading = false,
            downloadManager = vm.downloadManager,
            chats = chats,
        ),
        callback = object : HomeCallback {
            override fun onChatClick(id: Long) {
                scope.launch {
                    vm.onOpenChat(id) {
                        launch(Dispatchers.Main) {
                            navController.navigate(Screen.Chat.buildRoute(id))
                        }
                    }
                }
            }

            override fun onSearchClick() {
                // TODO
            }

            override fun onNewMessageClick() {
                navController.navigate(Screen.NewMessage.route)
            }
        })

}

