package com.ilhomsoliev.profile

import com.ilhomsoliev.profile.model.UserModel
import com.ilhomsoliev.profile.model.map
import com.ilhomsoliev.tgcore.TelegramClient
import com.ilhomsoliev.tgcore.send
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.drinkless.td.libcore.telegram.TdApi

class ProfileManager(
    private val tgClient: TelegramClient,
) {

    suspend fun getUserById(userId: Long) =
        tgClient.send<TdApi.User>(TdApi.GetUser(userId)).first().map()

    suspend fun getUserByIdAsFlow(userId: Long) =
        tgClient.send<TdApi.User>(TdApi.GetUser(userId)).map { it.map() }

    suspend fun getCurrentUser(): UserModel {
        return tgClient.send<TdApi.User>(TdApi.GetMe()).first().map()
    }

    suspend fun getContactsIds(
        query: String,
    ) =
        callbackFlow {
            tgClient.baseClient.send(
                if (query.isEmpty()) TdApi.GetContacts()
                else TdApi.SearchContacts(
                    /* query = */ query,
                    /* limit = */ 20
                )
            ) {
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