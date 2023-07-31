package com.ilhomsoliev.easytelegram.feature.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ilhomsoliev.easytelegram.data.TelegramClient
import com.ilhomsoliev.easytelegram.data.chats.ChatsRepository
import com.ilhomsoliev.easytelegram.data.messages.MessagesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.drinkless.td.libcore.telegram.TdApi

class ChatViewModel @OptIn(ExperimentalCoroutinesApi::class) constructor(
    private val chatsRepository: ChatsRepository,
    val client: TelegramClient,
    private val messagesRepository: MessagesRepository

) : ViewModel() {

    /*private val _chat = MutableStateFlow<TdApi.Chat?>(null)
    val chat = _chat.asStateFlow()*/

    var chat: Flow<TdApi.Chat?>? = null
        private set

    private val _answer = MutableStateFlow("")
    val answer = _answer.asStateFlow()

    var messagesPaged: Flow<PagingData<TdApi.Message>>? = null
        private set

    suspend fun loadChat(chatId: Long) {
        this.chat = chatsRepository.getChat(chatId)

        this.messagesPaged = Pager(PagingConfig(pageSize = 30)) {
            messagesRepository.getMessagesPaged(chatId)
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