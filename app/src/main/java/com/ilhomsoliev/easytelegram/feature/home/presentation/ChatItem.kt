package com.ilhomsoliev.easytelegram.feature.home.presentation

import android.text.format.DateUtils
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.easytelegram.data.TelegramClient
import com.ilhomsoliev.easytelegram.shared.TelegramImage
import org.drinkless.td.libcore.telegram.TdApi

@Composable
fun ChatTitle(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        maxLines = 1,
        // TODO style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.W500)
    )
}

@Composable
fun ChatSummary(chat: TdApi.Chat, modifier: Modifier = Modifier) {
    chat.lastMessage?.content?.let {
        when (it.constructor) {
            TdApi.MessageText.CONSTRUCTOR -> BasicChatSummary(
                text = (it as TdApi.MessageText).text.text,
                modifier = modifier,

                )

            TdApi.MessageVideo.CONSTRUCTOR -> HighlightedChatSummary("Video", modifier = modifier)
            TdApi.MessageCall.CONSTRUCTOR -> HighlightedChatSummary("Call", modifier = modifier)
            TdApi.MessageAudio.CONSTRUCTOR -> {
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
fun ChatItem(client: TelegramClient, chat: TdApi.Chat, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        TelegramImage(
            client = client,
            file = chat.photo?.small,
            modifier = Modifier
                .clip(shape = CircleShape)
                .size(52.dp)
        )
        Column(
            modifier = Modifier.padding(start = 12.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                ChatTitle(chat.title, modifier = Modifier.weight(1.0f))
                chat.lastMessage?.date?.toLong()?.let { it * 1000 }?.let {
                    ChatTime(it.toRelativeTimeSpan(), modifier = Modifier.alpha(0.6f))
                }
            }
            ChatSummary(chat)

        }
    }
}

private fun Long.toRelativeTimeSpan(): String =
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
