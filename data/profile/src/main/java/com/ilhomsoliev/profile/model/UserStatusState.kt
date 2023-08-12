package com.ilhomsoliev.profile.model

import org.drinkless.td.libcore.telegram.TdApi
import org.drinkless.td.libcore.telegram.TdApi.UserStatusEmpty
import org.drinkless.td.libcore.telegram.TdApi.UserStatusLastMonth
import org.drinkless.td.libcore.telegram.TdApi.UserStatusLastWeek
import org.drinkless.td.libcore.telegram.TdApi.UserStatusOffline
import org.drinkless.td.libcore.telegram.TdApi.UserStatusOnline
import org.drinkless.td.libcore.telegram.TdApi.UserStatusRecently

enum class UserStatusState {
    Empty,
    Online,
    Offline,
    Recently,
    LastWeek,
    LastMonth,
}

fun TdApi.UserStatus?.map() =
    when (this?.constructor) {
        UserStatusEmpty.CONSTRUCTOR -> com.ilhomsoliev.profile.model.UserStatusState.Empty
        UserStatusOnline.CONSTRUCTOR -> com.ilhomsoliev.profile.model.UserStatusState.Online
        UserStatusOffline.CONSTRUCTOR -> com.ilhomsoliev.profile.model.UserStatusState.Offline
        UserStatusRecently.CONSTRUCTOR -> com.ilhomsoliev.profile.model.UserStatusState.Recently
        UserStatusLastWeek.CONSTRUCTOR -> com.ilhomsoliev.profile.model.UserStatusState.LastWeek
        UserStatusLastMonth.CONSTRUCTOR -> com.ilhomsoliev.profile.model.UserStatusState.LastMonth
        else -> com.ilhomsoliev.profile.model.UserStatusState.Empty
    }