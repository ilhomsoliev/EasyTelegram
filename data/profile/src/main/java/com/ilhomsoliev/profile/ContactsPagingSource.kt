package com.ilhomsoliev.profile

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ilhomsoliev.profile.model.UserModel
import kotlinx.coroutines.flow.first

class ContactsPagingSource(
    private val profileRepository: ProfileRepository
) : PagingSource<Long, UserModel>() {
    override fun getRefreshKey(state: PagingState<Long, UserModel>): Long? = null

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, UserModel> {
        try {
            val nextPageNumber = params.key ?: Long.MAX_VALUE
            val response = profileRepository.getContacts()
            val contacts = response.first()
            return LoadResult.Page(
                data = contacts,
                prevKey = null,
                nextKey = null// TODO //contacts.lastOrNull()?.positions?.firstOrNull()?.order
            )

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }


}