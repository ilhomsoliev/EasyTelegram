package com.ilhomsoliev.chat.chats.repository

import com.ilhomsoliev.chat.chats.manager.ChatsManager
import com.ilhomsoliev.chat.model.chat.ChatModel
import com.ilhomsoliev.chat.model.chat.map
import com.ilhomsoliev.profile.ProfileRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
class ChatsRepository(
    private val chatsManager: ChatsManager,
    private val profileRepository: ProfileRepository,
) {
    suspend fun getChats(offsetOrder: Long = Long.MAX_VALUE, limit: Int): Flow<List<ChatModel>> =
        chatsManager.getChatIds(offsetOrder, limit)
            .map { ids -> ids.map { chatsManager.getChat(it) } }
            .flatMapLatest { chatsFlow ->
                combine(chatsFlow) { chats ->
                    chats.toList().map { it.map(profileRepository) }
                }
            }

    suspend fun getChat(chatId: Long) = chatsManager.getChat(chatId)

}