package com.ilhomsoliev.chat.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilhomsoliev.chat.chats.repository.ChatsRepository
import com.ilhomsoliev.chat.messages.repository.MessagesRepository
import com.ilhomsoliev.chat.messages.requests.SendMessageRequest
import com.ilhomsoliev.chat.model.message.MessageModel
import com.ilhomsoliev.chat.model.message.map
import com.ilhomsoliev.profile.ProfileRepository
import com.ilhomsoliev.shared.TgDownloadManager
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.drinkless.tdlib.TdApi

class ChatViewModel @OptIn(ExperimentalCoroutinesApi::class) constructor(
    private val chatsRepository: ChatsRepository,
    val downloadManager: TgDownloadManager,
    private val messagesRepository: MessagesRepository,
    private val profileRepository: ProfileRepository,
) : ViewModel() {

    private val _chat = MutableStateFlow<TdApi.Chat?>(null)
    val chat = _chat.asStateFlow()

    private val _answer = MutableStateFlow("")
    val answer = _answer.asStateFlow()

    private val _messages = MutableStateFlow<List<MessageModel>>(emptyList())
    val messages = _messages.asStateFlow()

    private val _lastMessageVisible = MutableStateFlow<MessageModel?>(null)
    val lastMessageVisible = _lastMessageVisible.asStateFlow()

    private var _messagesFetchingRunning = MutableStateFlow<Boolean?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun loadChat(chatId: Long) {
        _chat.value = chatsRepository.getChat(chatId).first()
    }

    fun sendMessage(
        chatId: Long,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0,
        options: TdApi.MessageSendOptions = TdApi.MessageSendOptions(),
        inputMessageContent: TdApi.InputMessageContent
    ): Deferred<TdApi.Message> {
        return messagesRepository.sendMessage(
            SendMessageRequest(
                chatId = chatId,
                messageThreadId = messageThreadId,
                replyTo = null, // TODO
                options = options,
                replyMarkup = null,// TODO
                inputMessageContent = inputMessageContent,
            )
        )
    }

    suspend fun clearAnswer() {
        _answer.emit("")
    }

    suspend fun changeAnswer(value: String) {
        _answer.emit(value)
    }

    private suspend fun loadMessages() {
        try {
            _messagesFetchingRunning.value = true
            fun addNewMessagesToList(messages: List<MessageModel>) {
                messages.lastOrNull()?.let { _lastMessageVisible.value = it }
                val newMessages = _messages.value.toMutableList()
                newMessages.addAll(messages)
                _messages.value = newMessages
            }
            chat.value?.id?.let {
                val response = messagesRepository.loadMessages(
                    chatId = it,
                    fromMessageId = _lastMessageVisible.value?.id ?: 0L
                ).first().messages.map { it.map(profileRepository) }
                Log.d("Hello Chat Screen response", response.size.toString())
                addNewMessagesToList(response)
            }
        } finally {
            _messagesFetchingRunning.value = false
        }

    }

    suspend fun onMessageItemPass(index: Int) {
        val isTaskRunning = _messagesFetchingRunning.value == true
        if (index != messages.value.size - 1) return // TODO
        if (isTaskRunning) return
        val message = messages.value.getOrNull(index)
        if (message?.id == _lastMessageVisible.value?.id || index == -1) {
            loadMessages()
        }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            val response = chat.value?.id?.let { chatsRepository.closeChat(it) }
            Log.d("Hello Chat Screen On Cleared", response.toString())
        }
    }
}