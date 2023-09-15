package com.ilhomsoliev.home.presentation.new_message

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.ilhomsoliev.home.viewmodel.NewMessagesViewModel
import kotlinx.coroutines.launch

@Composable
fun NewMessagesScreen(
    vm: NewMessagesViewModel,
    navController: NavController,
) {
    val scope = rememberCoroutineScope()

    val contacts = vm.contacts.collectAsLazyPagingItems()
    val isSearchVisible by vm.isSearchVisible.collectAsState()
    val searchRequest by vm.searchRequest.collectAsState()

    val downloadManager = vm.downloadManager

    NewMessagesContent(
        state = NewMessagesState(
            isSearchVisible = isSearchVisible,
            searchRequest = searchRequest,
            contacts = contacts,
            downloadManager = downloadManager
        ),
        callback = object : NewMessagesCallback {
            override fun onBackClick() {
                navController.popBackStack()
            }

            override fun onSearchClick() {
                scope.launch {
                    vm.onIsSearchVisibleChange(true)
                }
            }

            override fun onSearchRequestChange(value: String) {
                scope.launch {
                    vm.onSearchRequestChange(value)
                }
            }

            override fun onAddNewContactClick() {
                // OPen BS
            }

            override fun onClearSearchQuery() {
                scope.launch {
                    vm.onIsSearchVisibleChange(false)
                    vm.onSearchRequestChange("")
                }
            }
        })

}