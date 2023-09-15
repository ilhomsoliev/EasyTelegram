package com.ilhomsoliev.home.presentation.new_message

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
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
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.ilhomsoliev.profile.model.UserModel
import com.ilhomsoliev.shared.TgDownloadManager
import com.ilhomsoliev.shared.common.CustomSearchTextField
import com.ilhomsoliev.shared.common.items.ContactItem
import com.ilhomsoliev.shared.shared.icons.AddContactIcon

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
            TopAppBar(callback)
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
                Text(text = "Contacts")
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
    callback: NewMessagesCallback
) {
    val isSearchBarVisible = remember { mutableStateOf(false) }
    val searchRequest = remember { mutableStateOf("") }

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
                        modifier = Modifier.weight(1f),
                        value = searchRequest.value,
                        onValue = {
                            searchRequest.value = it
                        },
                        onCancelClick = {
                            isSearchBarVisible.value = false
                            searchRequest.value = ""
                        })
                }
            }

        }
        if (!isSearchBarVisible.value) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = {
                        isSearchBarVisible.value = true
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
    }
}