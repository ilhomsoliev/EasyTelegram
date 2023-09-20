package com.ilhomsoliev.chat.messages.manager

import android.util.Log
import com.ilhomsoliev.chat.messages.requests.SendMessageRequest
import com.ilhomsoliev.core.Constants.MESSAGES_LIST_THRESHOLD
import com.ilhomsoliev.tgcore.TelegramClient
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.drinkless.tdlib.TdApi

class MessagesManager(
    private val tgClient: TelegramClient
) {
    fun getMessages(
        chatId: Long,
        fromMessageId: Long,
        limit: Int,
        offset: Int,
    ): Flow<List<TdApi.Message>> =
        callbackFlow {
            tgClient.baseClient.send(
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
        tgClient.baseClient.send(TdApi.GetMessage(chatId, messageId)) {
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
        tgClient.baseClient.send(request) {
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

    fun loadMessages(chatId: Long, fromMessageId: Long) = callbackFlow {
        tgClient.baseClient.send(
            TdApi.GetChatHistory(
                /* chatId = */ chatId,
                /* fromMessageId = */ fromMessageId,
                /* offset = */ 0,
                /* limit = */ MESSAGES_LIST_THRESHOLD,
                /* onlyLocal = */ false,
            )
        ) {
            Log.d("OnResult LoadMessages", it.toString())
            when (it.constructor) {
                TdApi.Messages.CONSTRUCTOR -> {
                    trySend(it as TdApi.Messages).isSuccess
                }

                TdApi.Error.CONSTRUCTOR -> {

                }

                else -> {

                }
            }
        }
        awaitClose { }
    }
}