package com.ilhomsoliev.chat.model

import org.drinkless.td.libcore.telegram.TdApi
import org.drinkless.td.libcore.telegram.TdApi.MessageSendingStateFailed
import org.drinkless.td.libcore.telegram.TdApi.MessageSendingStatePending

enum class MessageSendingStateModel {
    PENDING, ERROR, SUCCESS
}

fun TdApi.MessageSendingState?.map(): MessageSendingStateModel =
    when (this?.constructor) {
        MessageSendingStatePending.CONSTRUCTOR -> MessageSendingStateModel.PENDING
        MessageSendingStateFailed.CONSTRUCTOR -> MessageSendingStateModel.ERROR
        else -> MessageSendingStateModel.SUCCESS
    }
