package com.ilhomsoliev.home.presentation.new_message

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.ilhomsoliev.profile.model.UserModel
import com.ilhomsoliev.shared.TgDownloadManager
import com.ilhomsoliev.shared.common.ChatItemImage

data class NewMessagesState(
    val isSearchActive: Boolean,
    val contacts: LazyPagingItems<UserModel>,
    val downloadManager: TgDownloadManager,
)

interface NewMessagesCallback {
    fun onBackClick()
    fun onSearchClick()
    fun onAddNewContactClick()
    fun onSearchQueryChange(value: String)
    fun onClearSearchQuery()

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewMessagesContent(
    state: NewMessagesState,
    callback: NewMessagesCallback,
) {
    val contacts = state.contacts.itemSnapshotList.items.toList()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Новое сообщение") }, navigationIcon = {
                IconButton(
                    onClick = {
                        callback.onBackClick()
                    }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
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
                IconButton(
                    onClick = {
                        callback.onAddNewContactClick()
                    }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                }
            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                callback.onAddNewContactClick()
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "New message"
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
        ) {
            item {
                Text(text = "Contacts")
            }

            items(
                count = state.contacts.itemCount,
                key = state.contacts.itemKey(),
                contentType = state.contacts.itemContentType()
            ) { index ->
                val contact = contacts[index]
                ContactItem(downloadManager = state.downloadManager, user = contact)
            }
        }
    }
}

@Composable
fun ContactItem(
    modifier: Modifier = Modifier,
    downloadManager: TgDownloadManager,
    user: UserModel,
) {
    Row(modifier = modifier) {
        ChatItemImage(
            modifier = Modifier
                .clip(shape = CircleShape)
                .size(60.dp),
            file = user.profilePhoto?.smallFile,
            userName = user.username,
            downloadManager = downloadManager,
        )
        //
        Column {
            Text(text = user.firstName)
            Text(text = user.status.name)
        }
    }

}