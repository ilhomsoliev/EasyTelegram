package com.ilhomsoliev.chat.chats.manager

import android.util.Log
import com.ilhomsoliev.tgcore.TelegramClient
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.drinkless.tdlib.TdApi

class ChatsManager(
    private val tgClient: TelegramClient,
) {

    fun getChat(chatId: Long): Flow<TdApi.Chat> = callbackFlow {
        tgClient.baseClient.send(TdApi.GetChat(chatId)) {
            when (it.constructor) {
                TdApi.Chat.CONSTRUCTOR -> {
                    trySend(it as TdApi.Chat).isSuccess
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
}