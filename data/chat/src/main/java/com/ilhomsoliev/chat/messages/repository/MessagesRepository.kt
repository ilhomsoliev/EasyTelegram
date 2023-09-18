package com.ilhomsoliev.chat.messages.repository

import androidx.paging.PagingSource
import com.ilhomsoliev.chat.messages.manager.MessagesManager
import com.ilhomsoliev.chat.messages.paging.MessagesPagingSource
import com.ilhomsoliev.chat.messages.requests.SendMessageRequest
import com.ilhomsoliev.chat.model.message.MessageModel
import com.ilhomsoliev.profile.ProfileRepository
import kotlinx.coroutines.Deferred
import org.drinkless.tdlib.TdApi

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
        sendMessageRequest: SendMessageRequest,
    ): Deferred<TdApi.Message> = messagesManager.sendMessage(sendMessageRequest)

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