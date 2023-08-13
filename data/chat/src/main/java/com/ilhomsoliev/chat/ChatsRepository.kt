package com.ilhomsoliev.chat

import com.ilhomsoliev.shared.TgDownloadManager
import com.ilhomsoliev.tgcore.TelegramClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import org.drinkless.td.libcore.telegram.TdApi

@ExperimentalCoroutinesApi
class ChatsRepository(
    tdLibParameters: TdApi.TdlibParameters,
    private val downloadManager: TgDownloadManager
) : TelegramClient(tdLibParameters) {

    private fun getChatIds(offsetOrder: Long = Long.MAX_VALUE, limit: Int): Flow<LongArray> =
        callbackFlow {
            baseClient.send(TdApi.GetChats(TdApi.ChatListMain(), limit)) {
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

    fun getChats(offsetOrder: Long = Long.MAX_VALUE, limit: Int): Flow<List<TdApi.Chat>> =
        getChatIds(offsetOrder, limit)
            .map { ids -> ids.map { getChat(it) } }
            .flatMapLatest { chatsFlow ->
                combine(chatsFlow) { chats ->
                    chats.toList()
                }
            }

    fun getChat(chatId: Long): Flow<TdApi.Chat> = callbackFlow {
        baseClient.send(TdApi.GetChat(chatId)) {
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
            //close()
        }
        awaitClose { }
    }

    fun chatImage(chat: TdApi.Chat): Flow<String?> =
        chat.photo?.small?.takeIf {
            it.local?.isDownloadingCompleted == false
        }?.id?.let { fileId ->
            downloadManager.downloadFile(fileId).map { chat.photo?.small?.local?.path }
        } ?: flowOf(chat.photo?.small?.local?.path)

}