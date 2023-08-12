package com.ilhomsoliev.chat.model

import org.drinkless.td.libcore.telegram.TdApi

enum class MessageSchedulingStateModel {
    SendAtDate, SendWhenOnline, Normal
}

fun TdApi.MessageSchedulingState?.map(): MessageSchedulingStateModel =
    when (this?.constructor) {
        TdApi.MessageSchedulingStateSendAtDate.CONSTRUCTOR -> MessageSchedulingStateModel.SendAtDate
        TdApi.MessageSchedulingStateSendWhenOnline.CONSTRUCTOR -> MessageSchedulingStateModel.SendWhenOnline
        else -> MessageSchedulingStateModel.Normal
    }