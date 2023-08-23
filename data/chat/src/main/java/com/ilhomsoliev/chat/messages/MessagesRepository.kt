package com.ilhomsoliev.chat.messages

import androidx.paging.PagingSource
import com.ilhomsoliev.chat.model.message.MessageModel
import com.ilhomsoliev.profile.ProfileRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.drinkless.td.libcore.telegram.TdApi

class MessagesRepository(private val client: com.ilhomsoliev.tgcore.TelegramClient) {

    fun getMessages(
        chatId: Long,
        fromMessageId: Long,
        limit: Int,
        offset: Int,
    ): Flow<List<TdApi.Message>> =
        callbackFlow {
            client.baseClient.send(
                TdApi.GetChatHistory(
                    /* chatId = */ chatId,
                    /* fromMessageId = */ fromMessageId,
                    /* offset = */ offset,
                    /* limit = */ limit,
                    /* onlyLocal = */ false
                )
            ) {
                when (it.constructor) {
                    TdApi.Messages.CONSTRUCTOR -> {
                        trySend((it as TdApi.Messages).messages.toList()).isSuccess
                    }

                    TdApi.Error.CONSTRUCTOR -> {
                        error("")
                    }

                    else -> {
                        error("")
                    }
                }
            }
            awaitClose { }
        }

    fun getMessagesPaged(
        chatId: Long,
        profileRepository: ProfileRepository
    ): PagingSource<Long, MessageModel> =
        MessagesPagingSource(chatId,
            this,
            profileRepository)

    fun getMessage(chatId: Long, messageId: Long): Flow<TdApi.Message> = callbackFlow {
        client.baseClient.send(TdApi.GetMessage(chatId, messageId)) {
            when (it.constructor) {
                TdApi.Message.CONSTRUCTOR -> {
                    trySend(it as TdApi.Message).isSuccess
                }

                TdApi.Error.CONSTRUCTOR -> {
                    error("Something went wrong")
                }

                else -> {
                    error("Something went wrong")
                }
            }
        }
        awaitClose { }
    }

    fun sendMessage(
        chatId: Long,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0,
        options: TdApi.MessageSendOptions = TdApi.MessageSendOptions(),
        inputMessageContent: TdApi.InputMessageContent
    ): Deferred<TdApi.Message> = sendMessage(
        TdApi.SendMessage(
            /* chatId = */ chatId,
            /* messageThreadId = */ messageThreadId,
            /* replyToMessageId = */ replyToMessageId,
            /* options = */ options,
            /* replyMarkup = */ null,
            /* inputMessageContent = */ inputMessageContent
        )
    )

    fun sendMessage(sendMessage: TdApi.SendMessage): Deferred<TdApi.Message> {
        val result = CompletableDeferred<TdApi.Message>()
        client.baseClient.send(sendMessage) {
            when (it.constructor) {
                TdApi.Message.CONSTRUCTOR -> {
                    result.complete(it as TdApi.Message)
                }

                else -> {
                    result.completeExceptionally(error("Something went wrong"))
                }
            }
        }
        return result
    }
}