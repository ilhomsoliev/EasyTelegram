package com.ilhomsoliev.chat.presentation.message_item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.Pending
import androidx.compose.material.icons.outlined.SyncProblem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilhomsoliev.chat.model.message.MessageModel
import com.ilhomsoliev.chat.model.message.MessageSendingStateModel
import com.ilhomsoliev.chat.model.message.messageContent.messageText.MessageTextModel
import com.ilhomsoliev.chat.presentation.message_types.TextMessage
import com.ilhomsoliev.shared.TelegramImage
import com.ilhomsoliev.shared.TgDownloadManager
import com.ilhomsoliev.shared.shared.icons.MessageTailIcon
import org.drinkless.td.libcore.telegram.TdApi
import java.util.Calendar
import java.util.Date

@Composable
fun MessageItem(
    isSameUserFromPreviousMessage: Boolean,
    isLastMessage: Boolean,
    isFirstMessage: Boolean,
    downloadManager: TgDownloadManager,
    message: MessageModel,
    modifier: Modifier = Modifier
) {
    if (message.isOutgoing) {
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
                        modifier = Modifier.align(Alignment.Bottom),
                        imageVector = MessageTailIcon,
                        contentDescription = null, colorFilter = ColorFilter.tint(Color.Red)
                    )
                }

            }
        }
    } else {
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.clickable(onClick = {}) then modifier.fillMaxWidth()
        ) {
            if (!isSameUserFromPreviousMessage) {
                ChatUserIcon(
                    downloadManager = downloadManager,
                    userPhotoFile = message.sender?.profilePhoto?.smallFile,
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(shape = CircleShape)
                        .size(42.dp)
                )
            } else {
                Box(
                    Modifier
                        .padding(8.dp)
                        .size(42.dp)
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
}

@Composable
private fun ChatUserIcon(
    downloadManager: TgDownloadManager,
    userPhotoFile: TdApi.File?,
    modifier: Modifier
) {
    TelegramImage(
        downloadManager = downloadManager,
        file = userPhotoFile,
        modifier = modifier
    )
}

@Composable
private fun MessageItemCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) = Box(
    modifier = modifier
        .clip(RoundedCornerShape(12.dp))
        .background(Color.Transparent),
    contentAlignment = Alignment.CenterEnd
) {
    content()
}

@Composable
private fun MessageItemContent(
    downloadManager: TgDownloadManager,
    message: MessageModel,
    modifier: Modifier = Modifier
) {
    when (message.content) {
        is MessageTextModel -> TextMessage(message, modifier)
        /*  is TdApi.MessageVideo -> VideoMessage(message, modifier)
          is TdApi.MessageCall -> CallMessage(message, modifier)
          is TdApi.MessageAudio -> AudioMessage(message, modifier)
          is TdApi.MessageSticker -> StickerMessage(downloadManager, message, modifier)
          is TdApi.MessageAnimation -> AnimationMessage(downloadManager, message, modifier)
          is TdApi.MessagePhoto -> PhotoMessage(downloadManager, message, Modifier)
          is TdApi.MessageVideoNote -> VideoNoteMessage(downloadManager, message, modifier)
          is TdApi.MessageVoiceNote -> VoiceNoteMessage(message, modifier)*/
        else -> UnsupportedMessage(modifier, message)
    }
}


@Composable
fun UnsupportedMessage(modifier: Modifier = Modifier, message: MessageModel) {
    Row(modifier = modifier) {
        Text(
            "Сообщение не поддерживаемое мессенджером",
            //   modifier = modifier,
            style = MaterialTheme.typography.labelMedium.copy(
                fontSize = 11.sp,
                fontWeight = FontWeight(500),
                color = Color(0xFF5F934B),
            )
        )
        MessageStatus(modifier = Modifier, message = message)
    }


}

@Composable
fun MessageStatus(message: MessageModel, modifier: Modifier = Modifier) {
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
        text = text,
        style = MaterialTheme.typography.labelMedium.copy(
            fontSize = 11.sp,
            fontWeight = FontWeight(500),
            color = Color(0xFF5F934B),
        ),
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
