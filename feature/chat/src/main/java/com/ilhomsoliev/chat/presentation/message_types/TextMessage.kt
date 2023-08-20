package com.ilhomsoliev.chat.presentation.message_types

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.chat.model.message.MessageModel
import com.ilhomsoliev.chat.model.message.messageContent.messageText.MessageTextModel
import com.ilhomsoliev.chat.presentation.message_item.MessageStatus
import com.ilhomsoliev.shared.shared.MessageTailIcon

@Composable
fun TextMessage(message: MessageModel, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        TextMessage(message.content as MessageTextModel)
        MessageStatus(modifier = Modifier.align(Alignment.BottomEnd), message = message)
    }
}

@Composable
private fun TextMessage(content: MessageTextModel, modifier: Modifier = Modifier) {
    Text(text = content.text, modifier = modifier)
}
