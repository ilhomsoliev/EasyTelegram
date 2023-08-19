package com.ilhomsoliev.chat.presentation.message_types

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.chat.model.message.MessageModel
import com.ilhomsoliev.chat.presentation.message_item.MessageStatus
import org.drinkless.td.libcore.telegram.TdApi


// TODO
@Composable
fun VoiceNoteMessage(
    message: MessageModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.End) {
        Text("<Voice note>")
        (message.content as TdApi.MessageVoiceNote).caption.text.takeIf { it.isNotBlank() }?.let {
            Text(it, Modifier.padding(4.dp, 4.dp, 4.dp, 0.dp))
        }
        MessageStatus(message)
    }
}