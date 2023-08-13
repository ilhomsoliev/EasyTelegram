package com.ilhomsoliev.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ilhomsoliev.chat.ChatsRepository
import com.ilhomsoliev.chat.messages.MessagesRepository
import com.ilhomsoliev.chat.model.message.MessageModel
import com.ilhomsoliev.profile.ProfileRepository
import com.ilhomsoliev.shared.TgDownloadManager
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.drinkless.td.libcore.telegram.TdApi

class ChatViewModel @OptIn(ExperimentalCoroutinesApi::class) constructor(
    private val chatsRepository: ChatsRepository,
    val downloadManager: TgDownloadManager,
    private val messagesRepository: MessagesRepository,
    private val profileRepository: ProfileRepository,
) : ViewModel() {
    var chat: Flow<TdApi.Chat?>? = null
        private set
    // Some comment

    private val _answer = MutableStateFlow("")
    val answer = _answer.asStateFlow()

    var messagesPaged: Flow<PagingData<MessageModel>>? = null
        private set

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun loadChat(chatId: Long) {
        this.chat = chatsRepository.getChat(chatId)

        this.messagesPaged = Pager(PagingConfig(pageSize = 30)) {
            messagesRepository.getMessagesPaged(chatId, profileRepository)
        }.flow.cachedIn(viewModelScope)

    }

    fun sendMessage(
        chatId: Long,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0,
        options: TdApi.MessageSendOptions = TdApi.MessageSendOptions(),
        inputMessageContent: TdApi.InputMessageContent
    ): Deferred<TdApi.Message> {
        return messagesRepository.sendMessage(
            chatId, messageThreadId, replyToMessageId, options, inputMessageContent
        )
    }

    suspend fun clearAnswer() {
        _answer.emit("")
    }

    suspend fun changeAnswer(value: String) {
        _answer.emit(value)
    }
}