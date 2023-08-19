package com.ilhomsoliev.chat.presentation.message_types

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ilhomsoliev.chat.model.message.MessageModel
import com.ilhomsoliev.chat.presentation.message_item.MessageStatus
import com.ilhomsoliev.shared.TelegramImage
import com.ilhomsoliev.shared.TgDownloadManager
import org.drinkless.td.libcore.telegram.TdApi

// TODO
@Composable
fun StickerMessage(
    downloadManager: TgDownloadManager,
    message: MessageModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.End) {
        StickerMessage(downloadManager, message.content as TdApi.MessageSticker)
        MessageStatus(message)
    }
}

// TODO
@Composable
private fun StickerMessage(
    downloadManager: TgDownloadManager,
    content: TdApi.MessageSticker,
    modifier: Modifier = Modifier
) {
    if (content.sticker.isAnimated) {
        Text(text = "<Animated Sticker> ${content.sticker.emoji}", modifier = modifier)
    } else {
        Box(contentAlignment = Alignment.BottomEnd) {
            TelegramImage(downloadManager = downloadManager, file = content.sticker.sticker)
            content.sticker.emoji.takeIf { it.isNotBlank() }?.let {
                Text(text = it, modifier = modifier)
            }
        }
    }
}