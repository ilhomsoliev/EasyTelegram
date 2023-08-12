package com.ilhomsoliev.chat.messages

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ilhomsoliev.chat.model.MessageModel
import com.ilhomsoliev.chat.model.map
import com.ilhomsoliev.profile.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import org.drinkless.td.libcore.telegram.TdApi

class MessagesPagingSource(
    private val chatId: Long,
    private val messagesRepository: MessagesRepository,
    private val profileRepository: ProfileRepository,
) : PagingSource<Long, MessageModel>() {

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, MessageModel> {
        return try {
            val response: Flow<List<TdApi.Message>> = messagesRepository.getMessages(
                chatId = chatId,
                fromMessageId = params.key ?: 0,
                limit = params.loadSize
            )
            val messages = response.first().map { it.map(profileRepository) }
            LoadResult.Page(
                data = messages,
                prevKey = null,
                nextKey = messages.lastOrNull()?.id
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Long, MessageModel>): Long? {
        return null
    }
}
