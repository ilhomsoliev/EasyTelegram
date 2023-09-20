package com.ilhomsoliev.chat.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilhomsoliev.chat.chats.repository.ChatsRepository
import com.ilhomsoliev.chat.messages.repository.MessagesRepository
import com.ilhomsoliev.chat.messages.requests.MarkMessagesAsViewedRequest
import com.ilhomsoliev.chat.messages.requests.SendMessageRequest
import com.ilhomsoliev.chat.model.chat.ChatModel
import com.ilhomsoliev.chat.model.chat.map
import com.ilhomsoliev.chat.model.message.MessageModel
import com.ilhomsoliev.chat.model.message.map
import com.ilhomsoliev.shared.TgDownloadManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.drinkless.tdlib.TdApi

class ChatViewModel constructor(
    private val chatsRepository: ChatsRepository,
    val downloadManager: TgDownloadManager,
    private val messagesRepository: MessagesRepository,
) : ViewModel() {

    private val _chat = MutableStateFlow<ChatModel?>(null)
    val chat = _chat.asStateFlow()

    private val _answer = MutableStateFlow("")
    val answer = _answer.asStateFlow()

    private val _messages = MutableStateFlow<List<MessageModel>>(emptyList())
    val messages = _messages.asStateFlow()

    private val _lastMessageVisible = MutableStateFlow<MessageModel?>(null)
    val lastMessageVisible = _lastMessageVisible.asStateFlow()

    private var _messagesFetchingRunning = MutableStateFlow<Boolean?>(null)
    var messagesFetchingRunning = _messagesFetchingRunning.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun loadChat(chatId: Long) {
        _chat.value = chatsRepository.getChat(chatId).first().map()
    }

    suspend fun sendMessage(
        chatId: Long,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0,
        options: TdApi.MessageSendOptions = TdApi.MessageSendOptions(),
        inputMessageContent: TdApi.InputMessageContent
    ) {
        val response = messagesRepository.sendMessage(
            SendMessageRequest(
                chatId = chatId,
                messageThreadId = messageThreadId,
                replyTo = null, // TODO
                options = options,
                replyMarkup = null,// TODO
                inputMessageContent = inputMessageContent,
            )
        )
        response.await()
        clearAnswer()
    }

    private suspend fun clearAnswer() {
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
                ).first().messages.map { it.map() }
                Log.d("Hello Chat Screen response", response.size.toString())
                addNewMessagesToList(response)
            }
        } finally {
            _messagesFetchingRunning.value = false
        }

    }

    suspend fun onMessageItemPass(index: Int) {
        suspend fun onLoadOldMessaged(index: Int) {
            val isTaskRunning = _messagesFetchingRunning.value == true
            if (index != messages.value.size - 1) return // TODO
            if (isTaskRunning) return
            val message = messages.value.getOrNull(index)
            if (message?.id == _lastMessageVisible.value?.id || index == -1) {
                this.loadMessages()
            }
        }

        fun markMessageAsRead(index: Int) {
            if (chat.value == null) return
            val message = messages.value.getOrNull(index)

            message?.let {
                if (message.id <= (chat.value?.lastMessage?.id ?: 0)) {
                    messagesRepository.markMessagesAsViewed(
                        MarkMessagesAsViewedRequest(
                            chat.value?.id!!,
                            listOf(message.id),
                        )
                    )
                }
            }
        }
        onLoadOldMessaged(index)
        markMessageAsRead(index)
    }


    suspend fun onMessageArrived(newMessage: MessageModel) {
        if (newMessage.chatId != chat.value?.id) return
        fun addNewMessageToList(newMessage: MessageModel) {
            val newList = messages.value.toMutableList()
            newList.add(0, newMessage)
            _messages.value = newList
        }
        addNewMessageToList(newMessage)
    }

    suspend fun clearHistory(alsoForOtherUser: Boolean) {
        chat.value?.id?.let { chatId ->
            val response = chatsRepository.clearChatHistory(
                chatId = chatId,
                removeFromChatList = true,
                alsoForOthers = alsoForOtherUser
            ).await() // TODO change constant true here

            if (response.constructor == TdApi.Ok().constructor) {
                _messages.value = emptyList()
            }
        }
    }

    suspend fun deleteChat(alsoForOtherUser: Boolean, onSuccess: () -> Unit) {
        chat.value?.id?.let { chatId ->
            val response = chatsRepository.deleteChat(chatId, alsoForOtherUser).await()
            if (response.constructor == TdApi.Ok().constructor) {
                _messages.value = emptyList()
                onSuccess()
            }
        }
    }

    suspend fun closeChat() {
        val response = chat.value?.id?.let { chatsRepository.closeChat(it) }
        Log.d("Hello Chat Screen On Cleared", response.toString())
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            closeChat()
        }
    }
}