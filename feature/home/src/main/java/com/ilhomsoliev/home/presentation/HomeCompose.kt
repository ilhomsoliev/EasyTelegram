package com.ilhomsoliev.home.presentation

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.ilhomsoliev.data.TelegramClient
import kotlinx.coroutines.launch
import org.drinkless.td.libcore.telegram.TdApi

data class HomeState(
    val isLoading: Boolean,
    val client: TelegramClient,
    val chats: LazyPagingItems<TdApi.Chat>,
)

interface HomeCallback {
    fun onChatClick(id: Long)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    state: HomeState,
    callback: HomeCallback,
) {
    val scope = rememberCoroutineScope()

    Log.d("Hello chats", state.chats.itemSnapshotList.items.toString())
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Easy Telegram") }, navigationIcon = {
                IconButton(
                    onClick = {
                        scope.launch {
                            //scaffoldState.drawerState.open()
                        }
                    }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = null
                    )
                }
            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                //    navController.navigate(Screen.CreateChat.route)
            }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "New message"
                )
            }
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LazyColumn(modifier = Modifier) {
                if (state.chats.loadState.refresh is LoadState.Loading) {
                    item {
                        CircularProgressIndicator() // TODO
                        //LoadingChats()
                    }
                }
                items(state.chats.itemSnapshotList.items, key = { key ->
                    key.id
                }) { item ->
                    item.let { chat ->
                        ChatItem(
                            client = state.client,
                            chat = chat,
                            modifier = Modifier
                                .padding(vertical = 4.dp, horizontal = 12.dp)
                                .clickable(onClick = {
                                    callback.onChatClick(item.id)
                                })
                        )
                        Divider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            thickness = 0.5.dp,
                            //startIndent = 64.dp
                        )
                    }
                }
            }
        }
    }
}