package com.ilhomsoliev.chat.model.message.messageContent.messageAnimation

import com.ilhomsoliev.chat.model.message.messageContent.MessageContentModel
import org.drinkless.tdlib.TdApi

// TODO get all fields
data class MessageAnimationModel(
    val text: String,
) : MessageContentModel()

fun TdApi.MessageAnimation.map() = MessageAnimationModel(
    text = caption.text ?: ""
)
