package com.ilhomsoliev.chat.presentation.message_types

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ilhomsoliev.chat.model.message.MessageModel
import com.ilhomsoliev.chat.presentation.message_item.MessageStatus
import org.drinkless.tdlib.TdApi

// TODO
@Composable
fun AudioMessage(message: MessageModel, modifier: Modifier = Modifier) {
    val content = message.content as TdApi.MessageAudio
    Column(modifier = modifier, horizontalAlignment = Alignment.End) {
        Text(text = "Audio ${content.audio.duration}", modifier = modifier)
        content.caption.text.takeIf { it.isNotBlank() }?.let {
            Text(it)
        }
        MessageStatus(message)
    }
}
