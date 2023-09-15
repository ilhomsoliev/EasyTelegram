package com.ilhomsoliev.chat.messages.repository

import androidx.paging.PagingSource
import com.ilhomsoliev.chat.messages.manager.MessagesManager
import com.ilhomsoliev.chat.messages.paging.MessagesPagingSource
import com.ilhomsoliev.chat.model.message.MessageModel
import com.ilhomsoliev.profile.ProfileRepository
import kotlinx.coroutines.Deferred
import org.drinkless.td.libcore.telegram.TdApi

class MessagesRepository(
    private val messagesManager: MessagesManager,
) {

    fun getMessagesPaged(
        chatId: Long,
        profileRepository: ProfileRepository
    ): PagingSource<Int, MessageModel> =
        MessagesPagingSource(
            chatId = chatId,
            messagesRepository = this,
            profileRepository = profileRepository
        )


    fun sendMessage(
        chatId: Long,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0,
        options: TdApi.MessageSendOptions = TdApi.MessageSendOptions(),
        inputMessageContent: TdApi.InputMessageContent
    ): Deferred<TdApi.Message> = messagesManager.sendMessage(
        TdApi.SendMessage(
            /* chatId = */ chatId,
            /* messageThreadId = */ messageThreadId,
            /* replyToMessageId = */ replyToMessageId,
            /* options = */ options,
            /* replyMarkup = */ null,
            /* inputMessageContent = */ inputMessageContent
        )
    )

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