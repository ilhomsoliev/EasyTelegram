package com.ilhomsoliev.chat.viewmodel

import androidx.lifecycle.ViewModel
import com.ilhomsoliev.chat.chats.repository.ChatsRepository
import com.ilhomsoliev.chat.messages.repository.MessagesRepository
import com.ilhomsoliev.chat.messages.requests.SendMessageRequest
import com.ilhomsoliev.chat.model.message.MessageModel
import com.ilhomsoliev.profile.ProfileRepository
import com.ilhomsoliev.shared.TgDownloadManager
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
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

    fun loadMessages() {
        chat.value?.id?.let { messagesRepository.loadMessages(it) }
    }
}