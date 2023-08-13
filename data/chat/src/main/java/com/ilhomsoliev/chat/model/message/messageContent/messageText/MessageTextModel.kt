package com.ilhomsoliev.chat.model.message.messageContent.messageText

import com.ilhomsoliev.chat.model.message.messageContent.MessageContentModel
import org.drinkless.td.libcore.telegram.TdApi

// TODO add entities and webPage
data class MessageTextModel(
    val text: String,
) : MessageContentModel()

fun TdApi.MessageText.map() = MessageTextModel(
    text = text.text ?: ""
)
