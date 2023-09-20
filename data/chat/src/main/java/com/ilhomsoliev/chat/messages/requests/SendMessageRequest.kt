package com.ilhomsoliev.chat.messages.requests

import org.drinkless.tdlib.TdApi

data class SendMessageRequest(
    val chatId: Long,
    val messageThreadId: Long = 0,
    val replyTo: MessageReplyToModel? = null,
    val options: TdApi.MessageSendOptions? = null,
    val replyMarkup: TdApi.ReplyMarkup? = null,
    val inputMessageContent: TdApi.InputMessageContent,
) {
    fun map() = TdApi.SendMessage().apply {
        chatId = this@SendMessageRequest.chatId
        messageThreadId = this@SendMessageRequest.messageThreadId
        replyTo = this@SendMessageRequest.replyTo?.map()
        options = this@SendMessageRequest.options
        replyMarkup = this@SendMessageRequest.replyMarkup
        inputMessageContent = this@SendMessageRequest.inputMessageContent
    }
}

sealed class MessageReplyToModel {
    data class MessageReplyToMessageModel(
        val chatId: Long,
        val messageId: Long,
    ) : MessageReplyToModel()

    data class MessageReplyToStoryModel(
        val storySenderChatId: Long,
        val storyId: Int,
    ) : MessageReplyToModel()

    fun map(): TdApi.MessageReplyTo {
        return if (this is MessageReplyToMessageModel) {
            val reply: MessageReplyToMessageModel = (this)
            TdApi.MessageReplyToMessage().apply {
                this.chatId = reply.chatId
                this.messageId = reply.messageId
            }
        } else {
            val reply = (this as MessageReplyToStoryModel)
            TdApi.MessageReplyToStory().apply {
                this.storyId = reply.storyId
                this.storySenderChatId = reply.storySenderChatId
            }
        }
    }
}