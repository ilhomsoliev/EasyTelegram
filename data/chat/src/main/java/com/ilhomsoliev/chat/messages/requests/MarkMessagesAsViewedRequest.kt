package com.ilhomsoliev.chat.messages.requests

import org.drinkless.tdlib.TdApi

data class MarkMessagesAsViewedRequest(
    val chatId: Long,
    val messageIds: List<Long>,
    val forceRead: Boolean = false,
) {
    fun map() = TdApi.ViewMessages().apply {
        chatId = this@MarkMessagesAsViewedRequest.chatId
        messageIds = this@MarkMessagesAsViewedRequest.messageIds.toLongArray()
        forceRead = this@MarkMessagesAsViewedRequest.forceRead
    }
}