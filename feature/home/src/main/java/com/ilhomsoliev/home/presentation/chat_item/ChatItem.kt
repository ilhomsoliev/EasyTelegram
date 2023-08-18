package com.ilhomsoliev.home.presentation.chat_item

import android.text.format.DateUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.chat.model.chat.ChatModel
import com.ilhomsoliev.chat.model.message.messageContent.messageText.MessageTextModel
import com.ilhomsoliev.shared.TelegramImage
import com.ilhomsoliev.shared.TgDownloadManager
import org.drinkless.td.libcore.telegram.TdApi


@Composable
fun ChatSummary(
    chat: ChatModel,
    modifier: Modifier = Modifier
) {
    chat.lastMessage?.content?.let {
        when (it) {
            is MessageTextModel -> BasicChatSummary(
                text = it.text,
                modifier = modifier,
            )

            //MessageVideoModel -> HighlightedChatSummary("Video", modifier = modifier)
            //TdApi.MessageCall.CONSTRUCTOR -> HighlightedChatSummary("Call", modifier = modifier)
            /* TdApi.MessageAudio.CONSTRUCTOR -> {
                 val message = it as TdApi.MessageAudio
                 Row(modifier = modifier) {
                     Icon(
                         imageVector = Icons.Default.Mic,
                         contentDescription = null
                     )
                     Text(
                         text = message.audio.duration.toTime(),
                         modifier = Modifier.padding(start = 8.dp)
                     )
                 }
             }

             TdApi.MessageSticker.CONSTRUCTOR -> BasicChatSummary(
                 (it as TdApi.MessageSticker).sticker.emoji + " Sticker",
                 modifier = modifier
             )

             TdApi.MessageAnimation.CONSTRUCTOR -> HighlightedChatSummary("GIF", modifier = modifier)
             TdApi.MessageLocation.CONSTRUCTOR -> HighlightedChatSummary(
                 "Location",
                 modifier = modifier
             )

             TdApi.MessageVoiceNote.CONSTRUCTOR -> {
                 val message = it as TdApi.MessageVoiceNote
                 Row(modifier = modifier) {
                     Icon(
                         imageVector = Icons.Default.Mic,
                         contentDescription = null
                     )
                     Text(
                         text = message.voiceNote.duration.toTime(),
                         modifier = Modifier.padding(start = 8.dp)
                     )
                 }
             }

             TdApi.MessageVideoNote.CONSTRUCTOR -> {
                 val message = it as TdApi.MessageVideoNote
                 Row(modifier = modifier) {
                     Icon(
                         imageVector = Icons.Default.Videocam,
                         contentDescription = null
                     )
                     Text(
                         text = message.videoNote.duration.toTime(),
                         modifier = Modifier.padding(start = 8.dp)
                     )
                 }
             }

             TdApi.MessageContactRegistered.CONSTRUCTOR -> HighlightedChatSummary(
                 "Joined Telegram!",
                 modifier = modifier
             )

             TdApi.MessageChatDeleteMember.CONSTRUCTOR -> HighlightedChatSummary(
                 "${(it as TdApi.MessageChatDeleteMember).userId} left the chat",
                 modifier = modifier
             )
 */
            else -> Text(it::class.java.simpleName)
        }
    }
}

@Composable
fun BasicChatSummary(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        // TODO style = MaterialTheme.typography.subtitle1,
        maxLines = 1,
        modifier = modifier,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun HighlightedChatSummary(text: String, modifier: Modifier = Modifier) {
    Text(
        text,
        // TODO style = MaterialTheme.typography.subtitle1,
        // TODO color = MaterialTheme.colors.primaryVariant,
        maxLines = 1,
        modifier = modifier,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun ChatTime(text: String, modifier: Modifier = Modifier) {
    Text(
        text,
        // TODO style = MaterialTheme.typography.caption,
        maxLines = 1,
        modifier = modifier,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun ChatItem(
    modifier: Modifier = Modifier,
    downloadManager: TgDownloadManager,
    chat: ChatModel,
) {
    Row(modifier = modifier) {
        ChatItemImage(
            downloadManager = downloadManager,
            file = chat.photo?.small,
            modifier = Modifier
                .clip(shape = CircleShape)
                .size(55.dp),
            userName = chat.themeName ?: ""
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ChatTitle(modifier = Modifier, chatModel = chat)
            ChatSummary(chat)
            Row(verticalAlignment = Alignment.CenterVertically) {

            }

        }
    }
}

@Composable
fun ChatItemImage(
    downloadManager: TgDownloadManager,
    file: TdApi.File?,
    userName: String,
    modifier: Modifier = Modifier,
) {
    if (file != null)
        TelegramImage(
            downloadManager = downloadManager,
            file = file,
            modifier = Modifier
                .clip(shape = CircleShape)
                .size(55.dp)
        )
    else {
        Box(modifier.background(Color.Red), contentAlignment = Alignment.Center) {
            Text(text = userName.take(2))
        }
    }
}

fun Long.toRelativeTimeSpan(): String =
    DateUtils.getRelativeTimeSpanString(
        this,
        System.currentTimeMillis(),
        DateUtils.SECOND_IN_MILLIS
    ).toString()

private fun Int.toTime(): String {
    val duration = this.toLong()
    val hours: Long = (duration / (60 * 60))
    val minutes = (duration % (60 * 60) / (60))
    val seconds = (duration % (60 * 60) % (60))
    return when {
        minutes == 0L && hours == 0L -> String.format("0:%02d", seconds)
        hours == 0L -> String.format("%02d:%02d", minutes, seconds)
        else -> String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}