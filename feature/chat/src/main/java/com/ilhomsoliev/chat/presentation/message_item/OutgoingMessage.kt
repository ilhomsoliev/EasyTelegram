package com.ilhomsoliev.chat.presentation.message_item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.chat.model.message.MessageModel
import com.ilhomsoliev.shared.TgDownloadManager
import com.ilhomsoliev.shared.shared.icons.MessageTailIcon

@Composable
fun OutgoingMessage(
    isSameUserFromPreviousMessage: Boolean,
    isLastMessage: Boolean,
    isFirstMessage: Boolean,
    downloadManager: TgDownloadManager,
    message: MessageModel,
    modifier: Modifier = Modifier
) {
    Box(
        Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomEnd
    ) {
        MessageItemCard(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(8.dp, 4.dp, 8.dp, 4.dp)
        ) {
            Row {
                MessageItemContent(
                    downloadManager = downloadManager,
                    message = message,
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                topStart = 12.dp,
                                topEnd = 12.dp,
                                bottomStart = 12.dp,
                                bottomEnd = if (isLastMessage) 4.dp else 12.dp
                            )
                        )
                        .background(Color(0xFFE3FFCA))
                        .clickable(onClick = {})
                        .padding(8.dp),
                )
                // if (isLastMessage)
                Image(
                    modifier = Modifier.align(Alignment.Bottom).offset(y = (-4).dp, x = (-1).dp),
                    imageVector = MessageTailIcon,
                    contentDescription = null, colorFilter = ColorFilter.tint(Color(0xFFE3FFCA))
                )
            }
        }
    }
}