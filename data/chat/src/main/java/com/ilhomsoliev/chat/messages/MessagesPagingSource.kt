package com.ilhomsoliev.chat.messages

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ilhomsoliev.chat.model.message.MessageModel
import com.ilhomsoliev.chat.model.message.map
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
            val page: Long = params.key ?: 1L
            val offset = (page - 1) * params.loadSize
            Log.d("Hello PAger", "$page ${params.loadSize}")
            val response: Flow<List<TdApi.Message>> = messagesRepository.getMessages(
                chatId = chatId,
                fromMessageId = params.key ?: 0,
                limit = params.loadSize,
                offset = offset.toInt(),
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

    /*override fun getRefreshKey(state: PagingState<Long, MessageModel>): Long? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null

        return (page.prevKey?.plus(1) ?: page.nextKey?.minus(1))?.toLong()
    }*/
    override fun getRefreshKey(state: PagingState<Long, MessageModel>): Long? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }


}
