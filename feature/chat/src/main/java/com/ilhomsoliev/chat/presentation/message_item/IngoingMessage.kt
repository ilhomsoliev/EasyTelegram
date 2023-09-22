package com.ilhomsoliev.chat.presentation.message_item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.chat.model.chat.ChatTypeModel
import com.ilhomsoliev.chat.model.message.MessageModel
import com.ilhomsoliev.shared.TgDownloadManager
import com.ilhomsoliev.shared.common.ChatItemImage

@Composable
fun IngoingMessage(
    isSameUserFromPreviousMessage: Boolean,
    isLastMessage: Boolean,
    isFirstMessage: Boolean,
    downloadManager: TgDownloadManager,
    message: MessageModel,
    modifier: Modifier = Modifier,
    chatType: ChatTypeModel,
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier.clickable(onClick = {}) then modifier.fillMaxWidth()
    ) {
        if (!isSameUserFromPreviousMessage && chatType != ChatTypeModel.ChatTypePrivate) {
            ChatItemImage(
                downloadManager = downloadManager,
                file = message.sender?.profilePhoto?.smallFile,
                imageSize = 42.dp,
                username = message.sender?.getFullName().toString(),
                modifier = Modifier
                    .padding(8.dp)
            )
        } else if (chatType != ChatTypeModel.ChatTypePrivate) {
            Box(
                Modifier
                    .padding(8.dp)
                    .size(42.dp)
            )
        } else {
            Box(
                Modifier
                    .padding(8.dp)
            )
        }
        MessageItemCard(modifier = Modifier.padding(0.dp, 4.dp, 8.dp, 4.dp)) {
            MessageItemContent(
                downloadManager = downloadManager,
                message = message,
                modifier = Modifier
                    .background(Color.White)
                    .padding(8.dp)
            )
        }
    }
}