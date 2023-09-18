package com.ilhomsoliev.chat.presentation.message_types

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.ilhomsoliev.chat.model.message.MessageModel
import com.ilhomsoliev.chat.presentation.message_item.MessageStatus
import com.ilhomsoliev.shared.TelegramImage
import com.ilhomsoliev.shared.TgDownloadManager
import org.drinkless.tdlib.TdApi

// TODO
@Composable
fun PhotoMessage(
    downloadManager: TgDownloadManager,
    message: MessageModel, modifier: Modifier = Modifier
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.End) {
        PhotoMessage(downloadManager, message.content as TdApi.MessagePhoto)
        MessageStatus(message, Modifier.padding(4.dp))
    }
}

@Composable
private fun PhotoMessage(
    downloadManager: TgDownloadManager,
    message: TdApi.MessagePhoto,
    modifier: Modifier = Modifier
) {
    val photo = message.photo.sizes.last()
    val width: Dp = with(LocalDensity.current) {
        photo.width.toDp()
    }
    Column(modifier.width(min(200.dp, width))) {
        TelegramImage(
            downloadManager = downloadManager,
            file = message.photo.sizes.last().photo,
            modifier = Modifier.fillMaxWidth()
        )
        message.caption.text.takeIf { it.isNotEmpty() }
            ?.let { Text(text = it, Modifier.padding(4.dp, 4.dp, 4.dp, 0.dp)) }
    }
}