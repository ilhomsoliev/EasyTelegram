package com.ilhomsoliev.profile.model

import org.drinkless.td.libcore.telegram.TdApi

data class UserModel(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val username: String,
    val phoneNumber: String,
    val status: UserStatusState,
    )


fun TdApi.User.map() = UserModel(
    id = id,
    firstName = firstName,
    lastName = lastName,
    username = username,
    phoneNumber = phoneNumber,
    status = status.map(),
)