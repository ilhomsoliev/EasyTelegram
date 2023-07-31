package com.ilhomsoliev.easytelegram.feature.home.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.ilhomsoliev.easytelegram.data.TelegramClient
import com.ilhomsoliev.easytelegram.data.auth.Authentication
import com.ilhomsoliev.easytelegram.data.chats.ChatsPagingSource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeViewModel(
    val client: TelegramClient,
    private val chatsPagingSource: ChatsPagingSource
) : ViewModel() {
    val uiState = mutableStateOf<UiState>(UiState.Loading)

    init {
        client.authState.onEach {
            when (it) {
                Authentication.UNAUTHENTICATED -> {
                    client.startAuthentication()
                }

                Authentication.WAIT_FOR_NUMBER, Authentication.WAIT_FOR_CODE, Authentication.WAIT_FOR_PASSWORD, Authentication.INCORRECT_CODE -> uiState.value =
                    UiState.Login

                Authentication.AUTHENTICATED -> uiState.value = UiState.Loaded
                Authentication.UNKNOWN -> {

                }
            }
        }.launchIn(viewModelScope)
    }

    val chats = Pager(
        PagingConfig(pageSize = 30)
    ) {
        chatsPagingSource
    }.flow.cachedIn(viewModelScope)
}

sealed class UiState {
    object Loading : UiState()
    object Login : UiState()
    object Loaded : UiState()
}