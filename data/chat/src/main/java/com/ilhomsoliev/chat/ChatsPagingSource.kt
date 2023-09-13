package com.ilhomsoliev.chat

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ilhomsoliev.chat.model.chat.ChatModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalCoroutinesApi::class)
class ChatsPagingSource(
    private val chats: ChatsRepository
) : PagingSource<Long, ChatModel>() {

    override fun getRefreshKey(state: PagingState<Long, ChatModel>): Long? {
        return null
    }
    override suspend fun load(
        params: LoadParams<Long>
    ): LoadResult<Long, ChatModel> {
        try {
            val nextPageNumber = params.key ?: Long.MAX_VALUE
            val response = chats.getChats(
                nextPageNumber,
                params.loadSize
            )
            val chats = response.first()
            return LoadResult.Page(
                data = chats,
                prevKey = null,
                nextKey = chats.lastOrNull()?.positions?.firstOrNull()?.order
            )

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}
