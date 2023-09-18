package com.ilhomsoliev.chat.model.message

import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.MessageSendingStateFailed
import org.drinkless.tdlib.TdApi.MessageSendingStatePending

enum class MessageSendingStateModel {
    PENDING, ERROR, SUCCESS
}

fun TdApi.MessageSendingState?.map(): MessageSendingStateModel =
    when (this?.constructor) {
        MessageSendingStatePending.CONSTRUCTOR -> MessageSendingStateModel.PENDING
        MessageSendingStateFailed.CONSTRUCTOR -> MessageSendingStateModel.ERROR
        else -> MessageSendingStateModel.SUCCESS
    }
