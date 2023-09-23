package com.ilhomsoliev.chat.chats.manager

import android.util.Log
import com.ilhomsoliev.tgcore.TelegramClient
import com.ilhomsoliev.tgcore.send
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.MessageSenderUser

class ChatsManager(
    private val tgClient: TelegramClient,
) {

    fun getChat(chatId: Long): Flow<TdApi.Chat> = callbackFlow {
        tgClient.baseClient.send(TdApi.GetChat(chatId)) {
            when (it.constructor) {
                TdApi.Chat.CONSTRUCTOR -> {
                    val chat = it as TdApi.Chat
                    trySend(chat).isSuccess
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

    fun getChatIds(offsetOrder: Long = Long.MAX_VALUE, limit: Int): Flow<LongArray> =
        callbackFlow {
            tgClient.baseClient.send(
                TdApi.GetChats(
                    TdApi.ChatListMain(), limit
                )
            ) {
                when (it.constructor) {
                    TdApi.Chats.CONSTRUCTOR -> {
                        trySend((it as TdApi.Chats).chatIds).isSuccess
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

    fun loadChat() {
        tgClient.baseClient.send(TdApi.LoadChats(null, 20)) {
            Log.d("OnResult Hello", it.toString())
        }
    }

    fun openChat(chatId: Long) = callbackFlow {
        tgClient.baseClient.send(TdApi.OpenChat(chatId)) {
            when (it.constructor) {
                TdApi.Ok.CONSTRUCTOR -> {
                    trySend(true)
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

    fun closeChat(chatId: Long) = callbackFlow {
        tgClient.baseClient.send(TdApi.CloseChat(chatId)) {
            when (it.constructor) {
                TdApi.Ok.CONSTRUCTOR -> {
                    trySend(true)
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

    fun clearChatHistory(
        chatId: Long,
        removeFromChatList: Boolean,
        alsoForOthers: Boolean
    ): CompletableDeferred<TdApi.Ok> {
        val result = CompletableDeferred<TdApi.Ok>()
        val request = TdApi.DeleteChatHistory()
        request.chatId = chatId
        request.removeFromChatList = removeFromChatList
        request.revoke = alsoForOthers
        tgClient.baseClient.send(request) {
            when (it.constructor) {
                TdApi.Ok.CONSTRUCTOR -> {
                    result.complete(it as TdApi.Ok)
                }

                else -> {
                    result.completeExceptionally(error("Something went wrong"))
                }
            }
        }
        return result
    }

    // TODO use delete alsoForOthers field
    fun deleteChat(chatId: Long, alsoForOthers: Boolean): CompletableDeferred<TdApi.Ok> {
        val result = CompletableDeferred<TdApi.Ok>()
        val request = TdApi.DeleteChat()
        request.chatId = chatId

        // request.revoke = alsoForOthers

        tgClient.baseClient.send(request) {
            when (it.constructor) {
                TdApi.Ok.CONSTRUCTOR -> {
                    result.complete(it as TdApi.Ok)
                }

                else -> {
                    result.completeExceptionally(error("Something went wrong"))
                }
            }
        }
        return result
    }
}