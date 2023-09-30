package com.ilhomsoliev.chat.presentation.message_types

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ilhomsoliev.chat.model.message.MessageModel
import com.ilhomsoliev.chat.model.message.messageContent.MessageContentModel
import com.ilhomsoliev.chat.presentation.message_item.MessageStatus

@Composable
fun TextMessage(message: MessageModel, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        TextMessage(message.content as MessageContentModel.MessageTextModel)
        MessageStatus(modifier = Modifier, message = message)
    }
}

@Composable
private fun TextMessage(content: MessageContentModel.MessageTextModel, modifier: Modifier = Modifier) {
    Text(
        text = content.text, modifier = modifier, style = MaterialTheme.typography.labelMedium.copy(
            fontSize = 15.sp,
            fontWeight = FontWeight(400),
            color = Color(0xFF232323)
        )
    )
}
