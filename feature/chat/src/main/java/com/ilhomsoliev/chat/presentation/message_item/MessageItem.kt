package com.ilhomsoliev.chat.presentation.message_item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilhomsoliev.chat.model.chat.ChatTypeModel
import com.ilhomsoliev.chat.model.message.MessageModel
import com.ilhomsoliev.chat.model.message.MessageSendingStateModel
import com.ilhomsoliev.chat.model.message.messageContent.MessageContentModel
import com.ilhomsoliev.chat.presentation.message_types.TextMessage
import com.ilhomsoliev.shared.TelegramImage
import com.ilhomsoliev.shared.TgDownloadManager
import org.drinkless.tdlib.TdApi
import java.util.Calendar
import java.util.Date

@Composable
fun MessageItem(
    isSameUserFromPreviousMessage: Boolean,
    isLastMessage: Boolean,
    isFirstMessage: Boolean,
    downloadManager: TgDownloadManager,
    message: MessageModel,
    modifier: Modifier = Modifier,
    chatType: ChatTypeModel,
) {
    if (message.isOutgoing) {
        OutgoingMessage(
            isSameUserFromPreviousMessage = isSameUserFromPreviousMessage,
            isLastMessage = isLastMessage,
            isFirstMessage = isFirstMessage,
            downloadManager = downloadManager,
            message = message
        )
    } else {
        IngoingMessage(
            isSameUserFromPreviousMessage = isSameUserFromPreviousMessage,
            isLastMessage = isLastMessage,
            isFirstMessage = isFirstMessage,
            downloadManager = downloadManager,
            message = message,
            chatType = chatType,
        )
    }
}

@Composable
internal fun ChatUserIcon(
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
internal fun MessageItemCard(
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
internal fun MessageItemContent(
    downloadManager: TgDownloadManager,
    message: MessageModel,
    modifier: Modifier = Modifier
) {
    when (message.content) {
        is MessageContentModel.MessageTextModel -> TextMessage(message, modifier)
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
