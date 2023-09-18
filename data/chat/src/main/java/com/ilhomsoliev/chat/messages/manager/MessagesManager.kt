package com.ilhomsoliev.chat.messages.manager

import android.util.Log
import com.ilhomsoliev.chat.messages.requests.SendMessageRequest
import com.ilhomsoliev.tgcore.TelegramClient
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.drinkless.tdlib.TdApi

class MessagesManager(
    private val client: TelegramClient
) {
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
                    /* fromMessageId = */ 0,
                    /* offset = */ offset,
                    /* limit = */ limit,
                    /* onlyLocal = */ false
                )
            ) {
                when (it.constructor) {
                    TdApi.Messages.CONSTRUCTOR -> {
                        val response = (it as TdApi.Messages).messages.toList()
                        Log.d(
                            "Hello Pager Response",
                            "${response.size} "
                        )
                        trySend(response).isSuccess
                    }

                    TdApi.Error.CONSTRUCTOR -> {
                        Log.d("Hello Pager", "Error ${it}")
                        error("")
                    }

                    else -> {
                        Log.d("Hello Pager", "Else ${it}")
                        error("")
                    }
                }
            }
            awaitClose { }
        }

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
        sendMessageRequest: SendMessageRequest,
    ): Deferred<TdApi.Message> {
        val result = CompletableDeferred<TdApi.Message>()
        val request = sendMessageRequest.map()
        client.baseClient.send(request) {
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