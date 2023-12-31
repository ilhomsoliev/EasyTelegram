package com.ilhomsoliev.chat.chats.repository

import com.ilhomsoliev.chat.chats.manager.ChatsManager
import com.ilhomsoliev.chat.model.chat.ChatModel
import com.ilhomsoliev.chat.model.chat.map
import com.ilhomsoliev.profile.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map


class ChatsRepository(
    private val chatsManager: ChatsManager,
    private val profileRepository: ProfileRepository,
) {
    fun getChat(chatId: Long) = chatsManager.getChat(chatId)

    suspend fun openChat(chatId: Long) = chatsManager.openChat(chatId).first()
    suspend fun closeChat(chatId: Long) = chatsManager.closeChat(chatId).first()

    fun loadChats() = chatsManager.loadChat()

    fun clearChatHistory(
        chatId: Long,
        removeFromChatList: Boolean,
        alsoForOthers: Boolean
    ) = chatsManager.clearChatHistory(
        chatId = chatId,
        removeFromChatList = removeFromChatList,
        alsoForOthers = alsoForOthers
    )

    fun deleteChat(chatId: Long, alsoForOthers: Boolean) =
        chatsManager.deleteChat(chatId, alsoForOthers)

}