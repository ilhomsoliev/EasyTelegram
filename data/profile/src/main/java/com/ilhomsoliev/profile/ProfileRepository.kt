package com.ilhomsoliev.profile

import com.ilhomsoliev.profile.model.UserModel
import com.ilhomsoliev.profile.model.map
import com.ilhomsoliev.tgcore.TelegramClient
import com.ilhomsoliev.tgcore.send
import kotlinx.coroutines.flow.first
import org.drinkless.td.libcore.telegram.TdApi

class ProfileRepository(
    private val tgClient: TelegramClient,
) {

    suspend fun getUserById(userId: Long): UserModel {
        return tgClient.send<TdApi.User>(TdApi.GetUser(userId)).first().map()
    }

    suspend fun getCurrentUser(): UserModel {
        return tgClient.send<TdApi.User>(TdApi.GetMe()).first().map()
    }

}