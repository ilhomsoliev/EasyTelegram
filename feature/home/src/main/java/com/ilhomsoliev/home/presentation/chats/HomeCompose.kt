package com.ilhomsoliev.home.presentation.chats

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.ilhomsoliev.chat.model.chat.ChatModel
import com.ilhomsoliev.core.Screen
import com.ilhomsoliev.home.presentation.chats.chat_item.ChatItem
import com.ilhomsoliev.profile.model.UserModel
import com.ilhomsoliev.shared.TgDownloadManager
import kotlinx.coroutines.launch

data class HomeState(
    val isLoading: Boolean,
    val downloadManager: TgDownloadManager,
    val currentUser: UserModel?,
    val chats: LazyPagingItems<ChatModel>,
)

interface HomeCallback {
    fun onChatClick(id: Long)
    fun onSearchClick()
    fun onNewMessageClick()

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    state: HomeState,
    callback: HomeCallback,
) {
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("BlaBlaChat") }, navigationIcon = {
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
            }, actions = {
                IconButton(
                    onClick = {
                        callback.onSearchClick()
                    }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                }
            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                callback.onNewMessageClick()
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
                state.currentUser?.let {
                    itemsIndexed(state.chats.itemSnapshotList.items, key = { index, key ->
                        key.id
                    }) { index, item ->
                        item.let { chat ->
                            ChatItem(
                                downloadManager = state.downloadManager,
                                chat = chat,
                                currentUser = state.currentUser,
                                modifier = Modifier
                                    .clickable(onClick = {
                                        callback.onChatClick(item.id)
                                    })

                            )
                        }
                    }
                }
            }
        }
    }
}