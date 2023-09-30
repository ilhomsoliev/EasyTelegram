package com.ilhomsoliev.tgcore

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.Message

val newUpdateFromTdApi = mutableStateOf<Boolean>(false)
val newMessageArrivedFromTdApi = mutableStateOf<Message?>(null)

private fun onNewUpdateFromTdApi() {
    newUpdateFromTdApi.value = !newUpdateFromTdApi.value
}

object UpdateHandler {

    /**
     * Updates for last messages in chat list
     */
    @OptIn(InternalCoroutinesApi::class)
    fun onUpdateChatLastMessage(update: TdApi.UpdateChatLastMessage) {
        val chat = AppDataState.getChat(update.chatId)
        chat?.let {
            synchronized(it) {
                chat.lastMessage = update.lastMessage
                AppDataState.putChat(update.chatId, chat)
                onNewUpdateFromTdApi()
            }
        }

    }

    @OptIn(InternalCoroutinesApi::class)
    fun onUpdateUserStatus(updateUserStatus: TdApi.UpdateUserStatus) {
        val user: TdApi.User? = AppDataState.getUser(updateUserStatus.userId)
        user?.let { synchronized(it) { user.status = updateUserStatus.status } }
    }

    fun onUpdateUser(updateUser: TdApi.UpdateUser) {
        AppDataState.putUser(updateUser.user.id, updateUser.user)
    }

    fun onUpdateNewChat(updateNewChat: TdApi.UpdateNewChat) {
        /*val chat = updateNewChat.chat
        synchronized(chat) {
            AppDataState.chats.put(chat.id, chat)
            val positions = chat.positions
            chat.positions = arrayOfNulls(0)
            setChatPositions(chat, positions)
        }*/
    }

    fun onUpdateFile(file: TdApi.UpdateFile) {
        AppDataState.putFile(file.file)
        onNewUpdateFromTdApi()

    }
}