package com.ilhomsoliev.chat.messages.repository

import androidx.paging.PagingSource
import com.ilhomsoliev.chat.messages.manager.MessagesManager
import com.ilhomsoliev.chat.messages.paging.MessagesPagingSource
import com.ilhomsoliev.chat.messages.requests.MarkMessagesAsViewedRequest
import com.ilhomsoliev.chat.messages.requests.SendMessageRequest
import com.ilhomsoliev.chat.model.message.MessageModel
import com.ilhomsoliev.profile.repository.ProfileRepository

class MessagesRepository(
    private val messagesManager: MessagesManager,
) {
    fun loadMessages(chatId: Long, fromMessageId: Long) =
        messagesManager.loadMessages(chatId, fromMessageId)

    fun sendMessage(
        sendMessageRequest: SendMessageRequest,
    ) = messagesManager.sendMessage(sendMessageRequest)

    fun markMessagesAsViewed(
        markMessagesAsViewedRequest: MarkMessagesAsViewedRequest,
    ) = messagesManager.markMessageAsViewed(markMessagesAsViewedRequest)

    fun getMessages(
        chatId: Long,
        fromMessageId: Long,
        limit: Int,
        offset: Int,
    ) = messagesManager.getMessages(
        chatId = chatId,
        fromMessageId = fromMessageId,
        limit = limit,
        offset = offset,
    )

}