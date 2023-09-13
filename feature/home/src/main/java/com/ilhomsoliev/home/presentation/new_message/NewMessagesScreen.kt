package com.ilhomsoliev.home.presentation.new_message

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.ilhomsoliev.home.viewmodel.NewMessagesViewModel

@Composable
fun NewMessagesScreen(
    vm: NewMessagesViewModel,
    navController: NavController,
) {

    val contacts = vm.contacts.collectAsLazyPagingItems()
    val downloadManager = vm.downloadManager
    NewMessagesContent(
        state = NewMessagesState(false, contacts, downloadManager),
        callback = object : NewMessagesCallback {
            override fun onBackClick() {
                navController.popBackStack()
            }

            override fun onSearchClick() {
                TODO("Not yet implemented")
            }

            override fun onAddNewContactClick() {
                TODO("Not yet implemented")
            }

            override fun onSearchQueryChange(value: String) {
                TODO("Not yet implemented")
            }

            override fun onClearSearchQuery() {
                TODO("Not yet implemented")
            }

        })

}