package com.ilhomsoliev.profile

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.ilhomsoliev.profile.model.UserModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class ProfileRepository(
    private val profileManager: ProfileManager,
    //private val tgClient: TelegramClient,
) {
    suspend fun getCurrentUser() = profileManager.getCurrentUser()
    suspend fun getUserById(userId: Long) = profileManager.getUserById(userId)

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getContacts(
        query: String,
    ): Flow<List<UserModel>> =
        profileManager.getContactsIds(
            query
        )
            .map { ids -> ids.map { profileManager.getUserByIdAsFlow(it) } }
            .flatMapLatest { usersFlow ->
                combine(usersFlow) { users ->
                    users.toList()
                }
            }

    fun getContactsPaging(
        query: String,
    ) = Pager(
        PagingConfig(pageSize = 30)
    ) {
        ContactsPagingSource(
            profileRepository = this,
            query = query,
        )
    }.flow


}