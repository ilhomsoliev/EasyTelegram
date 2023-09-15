package com.ilhomsoliev.home.presentation.chats.chat_item

import android.text.format.DateUtils
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilhomsoliev.chat.model.chat.ChatModel
import com.ilhomsoliev.chat.model.message.messageContent.messageText.MessageTextModel
import com.ilhomsoliev.profile.model.UserModel
import com.ilhomsoliev.shared.TgDownloadManager
import com.ilhomsoliev.shared.common.ChatItemImage
import com.ilhomsoliev.shared.shared.icons.PinnedIcon
import com.ilhomsoliev.shared.shared.utils.getPinnedModifier


@Composable
fun ChatSummary(
    chat: ChatModel,
    currentUser: UserModel,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = (-2).dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
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
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            if (chat.unreadCount != 0) {
                UnreadCount(chat.unreadCount)
            }
            if (chat.positions?.get(0)?.isPinned == true) {
                PinnedIndicator()
            }
        }
    }
}

@Composable
private fun UnreadCount(
    count: Int,
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color(0xFF007EEC)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = count.toString(),
            color = Color.White,
            modifier = Modifier.padding(horizontal = 5.dp)
        )
    }
}

@Composable
private fun PinnedIndicator() {
    Image(imageVector = PinnedIcon, contentDescription = null)
}

@Composable
fun BasicChatSummary(text: String, modifier: Modifier = Modifier) {

    val lineHeight = 13.sp * 4 / 3

    Text(
        modifier = modifier.sizeIn(minHeight = with(LocalDensity.current) {
            (lineHeight * 2).toDp()
        }),
        text = text,
        lineHeight = 16.sp,
        // TODO style = MaterialTheme.typography.subtitle1,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        fontSize = 15.sp,
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
fun ChatLastTimeIndicator(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
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
    currentUser: UserModel,
) {
    val localDensity = LocalDensity.current

    var columnHeightDp by remember {
        mutableStateOf(0.dp)
    }
    Column(
        modifier = modifier
            .then(getPinnedModifier(chat))
            .then(
                Modifier
                    .padding(top = 4.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            ChatItemImage(
                downloadManager = downloadManager,
                file = chat.photo?.small,
                modifier = Modifier,
                username = chat.title,
                imageSize = columnHeightDp,
            )
            Column(
                modifier = Modifier
                    .weight(1f, false)
                    .onGloballyPositioned { coordinates ->
                        columnHeightDp = with(localDensity) { coordinates.size.height.toDp() }
                    }
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                ChatTitle(
                    modifier = Modifier,
                    chatModel = chat,
                    currentUser = currentUser
                )
                ChatSummary(chat = chat, currentUser = currentUser)
            }
        }
        Divider(
            modifier = Modifier.padding(start = 71.dp, top = 4.dp),
            thickness = 0.5.dp,
        )
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
