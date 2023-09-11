package com.ilhomsoliev.home.presentation.chats.chat_item

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.shared.TelegramImage
import com.ilhomsoliev.shared.TgDownloadManager
import com.ilhomsoliev.shared.shared.utils.getChatEmptyProfileName
import org.drinkless.td.libcore.telegram.TdApi

@Composable
fun ChatItemImage(
    downloadManager: TgDownloadManager,
    file: TdApi.File?,
    userName: String,
    modifier: Modifier = Modifier,
) {
    if (file != null) {
        TelegramImage(
            downloadManager = downloadManager,
            file = file,
            modifier = Modifier
                .clip(shape = CircleShape)
                .size(55.dp)
        )
    } else {
        EmptyChatPhoto(
            modifier = modifier,
            name = userName,
        )
    }
}

@Composable
fun EmptyChatPhoto(
    modifier: Modifier = Modifier,
    name: String,
) {
    Box(
        modifier.background(Color.Red),
        contentAlignment = Alignment.Center
    ) {
        val namePIdozraz = getChatEmptyProfileName(name)
        Text(
            text = namePIdozraz,
            color = Color.White
        )
    }
}