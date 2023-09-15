package com.ilhomsoliev.home.presentation.new_message

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.ilhomsoliev.profile.model.UserModel
import com.ilhomsoliev.shared.TgDownloadManager
import com.ilhomsoliev.shared.common.CustomSearchTextField
import com.ilhomsoliev.shared.common.items.ContactItem
import com.ilhomsoliev.shared.shared.icons.AddContactIcon

data class NewMessagesState(
    val isSearchVisible: Boolean,
    val searchRequest: String,
    val contacts: LazyPagingItems<UserModel>,
    val downloadManager: TgDownloadManager,
)

interface NewMessagesCallback {
    fun onBackClick()
    fun onSearchClick()
    fun onSearchRequestChange(value: String)
    fun onAddNewContactClick()
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
            TopAppBar(state = state, callback = callback)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                callback.onAddNewContactClick()
            }) {
                Icon(
                    imageVector = AddContactIcon,
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFEFEFEF)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Контакты")
                }
            }

            items(
                count = state.contacts.itemCount,
                key = state.contacts.itemKey(),
                contentType = state.contacts.itemContentType()
            ) { index ->
                val contact = contacts[index]
                ContactItem(downloadManager = state.downloadManager, user = contact, onClick = {
                    // TODO
                })
            }
        }
    }
}

@Composable
fun TopAppBar(
    state: NewMessagesState,
    callback: NewMessagesCallback
) {
    val isSearchBarVisible = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                callback.onBackClick()
            }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
            AnimatedVisibility(
                visible = !isSearchBarVisible.value,
                modifier = Modifier,
                enter = fadeIn(), exit = fadeOut()
            ) {
                Text(text = "Новое сообщение")
            }
            AnimatedVisibility(
                modifier = Modifier.weight(1f),
                visible = isSearchBarVisible.value,
                enter = slideInHorizontally { it / 2 } + fadeIn(),
                exit = slideOutHorizontally { it / 2 } + fadeOut()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CustomSearchTextField(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 16.dp),
                        value = state.searchRequest,
                        onValue = {
                            callback.onSearchRequestChange(it)
                        },
                        onCancelClick = {
                            isSearchBarVisible.value = false
                            callback.onSearchRequestChange("")
                        })
                }
            }

        }
        if (!isSearchBarVisible.value) {
            TopAppBarActions(onSearchIconClick = {
                isSearchBarVisible.value = true
            }, callback = callback)
        }
    }
}

@Composable
fun TopAppBarActions(
    onSearchIconClick: () -> Unit,
    callback: NewMessagesCallback,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            onClick = {
                onSearchIconClick()
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
    }
}