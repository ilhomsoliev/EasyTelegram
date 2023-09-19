package com.ilhomsoliev.chat.model.message.messageContent

import org.drinkless.tdlib.TdApi

sealed class MessageContentModel {
    object NotDefined : MessageContentModel()

    data class MessageTextModel(
        val text: String,
    ) : MessageContentModel()

    data class MessageAnimationModel(
        val text: String,
    ) : MessageContentModel()

    data class MessageAudioModel(
        val caption: String,
    ) : MessageContentModel()



}
fun TdApi.MessageText.map() = MessageContentModel.MessageTextModel(
    text = text.text ?: ""
)
fun TdApi.MessageAnimation.map() = MessageContentModel.MessageAnimationModel(
    text = caption.text ?: ""
)
fun TdApi.MessageAudio.map() = MessageContentModel.MessageAudioModel(
    caption = this.caption.text ?: ""
)