package com.ilhomsoliev.profile

import com.ilhomsoliev.profile.model.UserModel
import com.ilhomsoliev.profile.model.map
import com.ilhomsoliev.tgcore.TelegramClient
import com.ilhomsoliev.tgcore.send
import kotlinx.coroutines.flow.first
import org.drinkless.td.libcore.telegram.TdApi

class ProfileRepository(
    tdLibParameters: TdApi.TdlibParameters
) : TelegramClient(tdLibParameters) {

    suspend fun getUser(userId: Long): UserModel {
        return send<TdApi.User>(TdApi.GetUser(userId)).first().map()
    }

}