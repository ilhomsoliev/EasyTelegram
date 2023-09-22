package com.ilhomsoliev.home.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilhomsoliev.auth.AuthRepository
import com.ilhomsoliev.chat.chats.repository.ChatsRepository
import com.ilhomsoliev.chat.model.chat.ChatModel
import com.ilhomsoliev.chat.model.chat.map
import com.ilhomsoliev.profile.repository.ProfileRepository
import com.ilhomsoliev.profile.model.UserModel
import com.ilhomsoliev.shared.TgDownloadManager
import com.ilhomsoliev.tgcore.AppDataState
import com.ilhomsoliev.tgcore.Authentication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeViewModel @OptIn(ExperimentalCoroutinesApi::class) constructor(
    private val authRepository: AuthRepository,
    val downloadManager: TgDownloadManager,
    private val profileRepository: ProfileRepository,
    private val chatsRepository: ChatsRepository,
) : ViewModel() {

    val uiState = mutableStateOf<UiState>(UiState.Loading)

    private val _user = MutableStateFlow<UserModel?>(null)
    val user = _user.asStateFlow()

    private val _chats = MutableStateFlow<List<ChatModel>>(emptyList())
    val chats = _chats.asStateFlow()

    init {
        authRepository.authState.onEach {
            Log.d("Hello authState", it.toString())
            when (it) {
                Authentication.UNAUTHENTICATED -> {
                    authRepository.startAuthentication()
                }

                Authentication.WAIT_FOR_NUMBER,
                Authentication.WAIT_FOR_CODE,
                Authentication.WAIT_FOR_PASSWORD,
                Authentication.INCORRECT_CODE -> uiState.value =
                    UiState.Login

                Authentication.AUTHENTICATED -> uiState.value = UiState.Loaded
                Authentication.UNKNOWN -> {
                    // uiState.value = UiState.Login
                }
            }
        }.launchIn(viewModelScope)
    }

    suspend fun getUser() {
        val user = profileRepository.getCurrentUser()
        _user.value = (user)
    }

    suspend fun onOpenChat(chatId: Long, callback: () -> Unit) {
        if (chatsRepository.openChat(chatId)) {
            callback()
        }
    }

    fun loadChats() {
        chatsRepository.loadChats()
    }

    suspend fun updateChats() {
        val chats = AppDataState.getChats().map { it.map() }
        _chats.emit(chats)
    }

}

sealed class UiState {
    object Loading : UiState()
    object Login : UiState()
    object Loaded : UiState()
}