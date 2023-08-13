package com.ilhomsoliev.chat.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.Pending
import androidx.compose.material.icons.outlined.SyncProblem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.ilhomsoliev.chat.model.message.MessageModel
import com.ilhomsoliev.chat.model.message.MessageSendingStateModel
import com.ilhomsoliev.chat.model.message.messageContent.messageText.MessageTextModel
import com.ilhomsoliev.shared.TelegramImage
import com.ilhomsoliev.shared.TgDownloadManager
import org.drinkless.td.libcore.telegram.TdApi
import java.util.Calendar
import java.util.Date

@Composable
fun TextMessage(message: MessageModel, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.End) {
        TextMessage(message.content as MessageTextModel)
        MessageStatus(message)
    }
}

@Composable
private fun TextMessage(content: MessageTextModel, modifier: Modifier = Modifier) {
    Text(text = content.text, modifier = modifier)
}

// TODO
@Composable
fun AudioMessage(message: MessageModel, modifier: Modifier = Modifier) {
    val content = message.content as TdApi.MessageAudio
    Column(modifier = modifier, horizontalAlignment = Alignment.End) {
        Text(text = "Audio ${content.audio.duration}", modifier = modifier)
        content.caption.text.takeIf { it.isNotBlank() }?.let {
            Text(it)
        }
        MessageStatus(message)
    }
}

// TODO
@Composable
fun VideoMessage(message: MessageModel, modifier: Modifier = Modifier) {
    val content = message.content as TdApi.MessageVideo
    Column(modifier = modifier, horizontalAlignment = Alignment.End) {
        Text(text = "Video ${content.video.duration}", modifier = modifier)
        content.caption.text.takeIf { it.isNotBlank() }?.let {
            Text(it)
        }
        MessageStatus(message)
    }
}

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

// TODO
@Composable
fun CallMessage(message: MessageModel, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.End) {
        CallMessage(message.content as TdApi.MessageCall)
        MessageStatus(message)
    }
}

@Composable
private fun CallMessage(content: TdApi.MessageCall, modifier: Modifier = Modifier) {
    val msg = when (content.discardReason) {
        is TdApi.CallDiscardReasonHungUp -> {
            "Incoming call"
        }

        is TdApi.CallDiscardReasonDeclined -> {
            "Declined call"
        }

        is TdApi.CallDiscardReasonDisconnected -> {
            "Call disconnected"
        }

        is TdApi.CallDiscardReasonMissed -> {
            "Missed call"
        }

        is TdApi.CallDiscardReasonEmpty -> {
            "Call: Unknown state"
        }

        else -> "Call: Unknown state"
    }
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(text = msg, modifier = modifier)
        Icon(
            imageVector = Icons.Outlined.Call,
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .size(18.dp)
        )
    }
}

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

// TODO
@Composable
fun VoiceNoteMessage(
    message: MessageModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.End) {
        Text("<Voice note>")
        (message.content as TdApi.MessageVoiceNote).caption.text.takeIf { it.isNotBlank() }?.let {
            Text(it, Modifier.padding(4.dp, 4.dp, 4.dp, 0.dp))
        }
        MessageStatus(message)
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

@Composable
fun UnsupportedMessage(modifier: Modifier = Modifier, title: String? = null) {
    Text(title ?: "<Unsupported message>", modifier = modifier)
}

@Composable
private fun MessageStatus(message: MessageModel, modifier: Modifier = Modifier) {
    if (message.isOutgoing) {
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            MessageTime(message = message)
            MessageSendingState(message.sendingStateModel, modifier.size(16.dp))
        }
    } else {
        MessageTime(message = message, modifier = modifier)
    }
}

@Composable
private fun MessageTime(message: MessageModel, modifier: Modifier = Modifier) {
    val date = Date(message.date.toLong())
    val calendar = Calendar.getInstance().apply { time = date }
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)
    MessageTime(text = "$hour:$minute", modifier = modifier.alpha(0.6f))
}

@Composable
private fun MessageTime(text: String, modifier: Modifier = Modifier) {
    Text(
        text,
        //style = MaterialTheme.typography.caption,
        maxLines = 1,
        modifier = modifier
    )
}

@Composable
private fun MessageSendingState(
    sendingState: MessageSendingStateModel?,
    modifier: Modifier = Modifier
) {
    when (sendingState) {
        MessageSendingStateModel.PENDING -> {
            Icon(
                imageVector = Icons.Outlined.Pending,
                contentDescription = null,
                modifier = modifier
            )
        }

        MessageSendingStateModel.ERROR -> {
            Icon(
                imageVector = Icons.Outlined.SyncProblem,
                contentDescription = null,
                modifier = modifier
            )
        }

        else -> {
            Icon(
                imageVector = Icons.Outlined.Done,
                contentDescription = null,
                modifier = modifier
            )
        }
    }
}
