package com.ilhomsoliev.chat.presentation.message_types

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.ilhomsoliev.chat.model.message.MessageModel
import com.ilhomsoliev.shared.TgDownloadManager
import org.drinkless.td.libcore.telegram.TdApi

// TODO
@Composable
fun AnimationMessage(
    downloadManager: TgDownloadManager,
    message: MessageModel,
    modifier: Modifier = Modifier
) {
    val content = message.content as TdApi.MessageAnimation
    val path =
        downloadManager.downloadableFile(content.animation.animation).collectAsState(initial = null)
    Column {
        path.value?.let { filePath ->
            /*CoilImage(data = File(filePath), modifier = Modifier.size(56.dp)) {

            }*/
        } ?: Text(text = "path null", modifier = modifier)
        Text(text = "path: ${path.value}")
    }
}