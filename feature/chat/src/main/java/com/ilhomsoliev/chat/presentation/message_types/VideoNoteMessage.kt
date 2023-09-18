package com.ilhomsoliev.chat.presentation.message_types

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.chat.model.message.MessageModel
import com.ilhomsoliev.chat.presentation.message_item.MessageStatus
import com.ilhomsoliev.shared.TelegramImage
import com.ilhomsoliev.shared.TgDownloadManager
import org.drinkless.tdlib.TdApi


// TODO
@Composable
fun VideoNoteMessage(
    downloadManager: TgDownloadManager,
    message: MessageModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.End) {
        Text("<Video note>")
        TelegramImage(
            downloadManager = downloadManager,
            file = (message.content as TdApi.MessageVideoNote).videoNote.thumbnail?.file,
            modifier = Modifier.size(150.dp)
        )
        MessageStatus(message)
    }
}