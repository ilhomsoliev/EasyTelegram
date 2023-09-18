package com.ilhomsoliev.chat.model.chat

import org.drinkless.tdlib.TdApi

enum class ChatTypeModel {
    ChatTypePrivate,
    ChatTypeBasicGroup,
    ChatTypeSupergroup,
    ChatTypeSecret,
}

fun TdApi.ChatType.map() = when (this.constructor) {
    TdApi.ChatTypePrivate.CONSTRUCTOR -> ChatTypeModel.ChatTypePrivate
    TdApi.ChatTypeBasicGroup.CONSTRUCTOR -> ChatTypeModel.ChatTypeBasicGroup
    TdApi.ChatTypeSupergroup.CONSTRUCTOR -> ChatTypeModel.ChatTypeSupergroup
    TdApi.ChatTypeSecret.CONSTRUCTOR -> ChatTypeModel.ChatTypeSecret
    else -> {
        ChatTypeModel.ChatTypePrivate
    }
}