package com.ilhomsoliev.profile

import com.ilhomsoliev.profile.model.UserModel
import com.ilhomsoliev.profile.model.map
import com.ilhomsoliev.tgcore.TelegramClient
import com.ilhomsoliev.tgcore.send
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import org.drinkless.td.libcore.telegram.TdApi

class ProfileRepository(
    private val tgClient: TelegramClient,
) {

    suspend fun getUserById(userId: Long) =
        tgClient.send<TdApi.User>(TdApi.GetUser(userId)).first().map()

    suspend fun getUserByIdAsFlow(userId: Long) =
        tgClient.send<TdApi.User>(TdApi.GetUser(userId)).map { it.map() }


    suspend fun getCurrentUser(): UserModel {
        return tgClient.send<TdApi.User>(TdApi.GetMe()).first().map()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getContacts(): Flow<List<UserModel>> =
        getContactsIds()
            .map { ids -> ids.map { getUserByIdAsFlow(it) } }
            .flatMapLatest { usersFlow ->
                combine(usersFlow) { users ->
                    users.toList()
                }
            }


    private suspend fun getContactsIds() =
        callbackFlow {
            tgClient.baseClient.send(TdApi.GetContacts()) {
                when (it.constructor) {
                    TdApi.Users.CONSTRUCTOR -> {
                        trySend((it as TdApi.Users).userIds).isSuccess
                    }

                    TdApi.Error.CONSTRUCTOR -> {
                        error("Some Error")
                    }

                    else -> {
                        error("Some Error")
                    }
                }
            }
            awaitClose {}
        }

}