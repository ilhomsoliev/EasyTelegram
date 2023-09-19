package com.ilhomsoliev.tgcore

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import org.drinkless.tdlib.TdApi

val newUpdateFromTdApi = mutableStateOf<Boolean>(false)

private fun onNewUpdateFromTdApi() {
    newUpdateFromTdApi.value = !newUpdateFromTdApi.value
}

object UpdateHandler {

    /**
     * Updates for last messages in chat list
     */
    fun onUpdateChatLastMessage(update: TdApi.UpdateChatLastMessage) {
        val chat = AppDataState.getChat(update.chatId)
        Log.d("Hello onUpdateChatLastMessage", update.lastMessage?.content.toString())
        chat?.lastMessage = update.lastMessage
        chat?.let { AppDataState.putChat(update.chatId, it) }
        onNewUpdateFromTdApi()
    }

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

}