package com.ilhomsoliev.chat.messages.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ilhomsoliev.chat.messages.repository.MessagesRepository
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
) : PagingSource<Int, MessageModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MessageModel> {
        return try {
            val page: Int = params.key ?: 1
            val offset = (page - 1) * params.loadSize
            val response: Flow<List<TdApi.Message>> = messagesRepository.getMessages(
                chatId = chatId,
                fromMessageId = 0,
                limit = params.loadSize,
                offset = offset * -1,
            )

            val messages = response.first().map { it.map(profileRepository) }
            Log.d("Hello Pager", "$page ${params.loadSize} $offset")

            val prevKey = if (page > 1) page.minus(1) else null
            val nextKey = if (response.first().isNotEmpty()) page.plus(1) else null

            LoadResult.Page(
                data = messages,
                prevKey = prevKey,
                nextKey = nextKey,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MessageModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: if (state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1) != 0) state.closestPageToPosition(
                    anchorPosition
                )?.nextKey?.minus(1) else null
        }
    }
}
