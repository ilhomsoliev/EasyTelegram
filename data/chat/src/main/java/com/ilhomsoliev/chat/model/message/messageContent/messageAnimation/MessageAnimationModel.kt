package com.ilhomsoliev.chat.model.message.messageContent.messageAnimation

import com.ilhomsoliev.chat.model.message.messageContent.MessageContentModel
import org.drinkless.td.libcore.telegram.TdApi

// TODO get all fields
data class MessageAnimationModel(
    val text: String,
) : MessageContentModel()

fun TdApi.MessageAnimation.map() = MessageAnimationModel(
    text = caption.text ?: ""
)
