package com.ilhomsoliev.shared.common

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
import androidx.compose.ui.unit.Dp
import com.ilhomsoliev.shared.TelegramImage
import com.ilhomsoliev.shared.TgDownloadManager
import com.ilhomsoliev.shared.shared.utils.getChatEmptyProfileName
import org.drinkless.tdlib.TdApi

@Composable
fun ChatItemImage(
    modifier: Modifier = Modifier,
    downloadManager: TgDownloadManager,
    file: TdApi.File?,
    username: String,
    imageSize: Dp
) {
    if (file != null) {
        TelegramImage(
            downloadManager = downloadManager,
            file = file,
            modifier = Modifier
                .clip(shape = CircleShape)
                .size(imageSize)
        )
    } else {
        EmptyChatPhoto(
            modifier = modifier
                .clip(shape = CircleShape)
                .size(imageSize),
            name = username,
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
        val nameOro = getChatEmptyProfileName(name)
        Text(
            text = nameOro,
            color = Color.White
        )
    }
}