package com.ilhomsoliev.home.presentation.chat_item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.ilhomsoliev.chat.model.chat.ChatModel

@Composable
fun ChatTitle(
    modifier: Modifier = Modifier,
    chatModel: ChatModel,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = chatModel.title,
                maxLines = 1,
            )
            if (chatModel.defaultDisableNotification) {
                // TODO Icon mute
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text =
                if (chatModel.lastReadInboxMessageId == chatModel.lastMessage?.id) "1 - "
                else "0 - "
            )
            chatModel.lastMessage?.date?.toLong()?.let { it * 1000 }?.let {
                ChatTime(it.toRelativeTimeSpan(), modifier = Modifier.alpha(0.6f))
            }
        }
    }

}
