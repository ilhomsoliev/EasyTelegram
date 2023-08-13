package com.ilhomsoliev.chat.model.message.messageContent.messageAudio

import com.ilhomsoliev.chat.model.message.messageContent.MessageContentModel
import org.drinkless.td.libcore.telegram.TdApi

// TODO add all fields
data class MessageAudioModel(
    val caption: String,
) : MessageContentModel()

fun TdApi.MessageAudio.map() = MessageAudioModel(
    caption = this.caption.text ?: ""
)