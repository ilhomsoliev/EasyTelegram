package com.ilhomsoliev.profile.model

import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.UserStatusEmpty
import org.drinkless.tdlib.TdApi.UserStatusLastMonth
import org.drinkless.tdlib.TdApi.UserStatusLastWeek
import org.drinkless.tdlib.TdApi.UserStatusOffline
import org.drinkless.tdlib.TdApi.UserStatusOnline
import org.drinkless.tdlib.TdApi.UserStatusRecently

enum class UserStatusState1 {
    Empty,
    Online,
    Offline,
    Recently,
    LastWeek,
    LastMonth,
}

sealed class UserStatusState {
    object Empty : UserStatusState()
    data class Online(val expires: Int) : UserStatusState()
    data class Offline(val wasOnline: Int) : UserStatusState()
    object Recently : UserStatusState()
    object LastWeek : UserStatusState()
    object LastMonth : UserStatusState()

}

fun TdApi.UserStatus?.map() =
    when (this?.constructor) {
        UserStatusEmpty.CONSTRUCTOR -> UserStatusState.Empty
        UserStatusOnline.CONSTRUCTOR -> UserStatusState.Online((this as UserStatusOnline).expires)
        UserStatusOffline.CONSTRUCTOR -> UserStatusState.Offline((this as UserStatusOffline).wasOnline)
        UserStatusRecently.CONSTRUCTOR -> UserStatusState.Recently
        UserStatusLastWeek.CONSTRUCTOR -> UserStatusState.LastWeek
        UserStatusLastMonth.CONSTRUCTOR -> UserStatusState.LastMonth
        else -> UserStatusState.Empty
    }