package com.ilhomsoliev.chat.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Gif
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.AttachFile
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.ilhomsoliev.tgcore.TelegramClient
import org.drinkless.td.libcore.telegram.TdApi

data class ChatState(
    val chat: TdApi.Chat? = null,
    val answer: String,
    val client: TelegramClient,
    val messages: LazyPagingItems<TdApi.Message>?,
)

interface ChatCallback {
    fun onAnswerChange(value: String)
    fun onSendMessage()
    fun onBack()

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatContent(
    state: ChatState,
    callback: ChatCallback
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        callback.onBack()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                    com.ilhomsoliev.shared.TelegramImage(
                        modifier = Modifier.clip(CircleShape),
                        client = state.client,
                        file = state.chat?.photo?.small
                    )
                    Text(text = state.chat?.title ?: "")
                }
                IconButton(onClick = {

                }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                }
            }

        },
        bottomBar = {
            MessageInput(
                input = state.answer,
                insertGif = {
                    // TODO
                }, attachFile = {
                    // todo
                }, sendMessage = {
                    callback.onSendMessage()
                    /*viewModel.sendMessage(
                        inputMessageContent = TdApi.InputMessageText(
                            TdApi.FormattedText(
                                it,
                                emptyArray()
                            ), false, false
                        )
                    ).await()
                    input.value = TextFieldValue()
                    history.refresh()*/
                }, onInputChange = {
                    callback.onAnswerChange(it)
                })
        }
    ) {
        state.messages?.run {
            Log.d("Hello messages", this.itemSnapshotList.items.toString())
            ChatHistory(
                client = state.client,
                messages = this,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageInput(
    modifier: Modifier = Modifier,
    input: String,
    onInputChange: (String) -> Unit,
    insertGif: () -> Unit = {},
    attachFile: () -> Unit = {},
    sendMessage: () -> Unit,
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 6.dp
    ) {
        TextField(
            value = input,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { onInputChange(it) },
            // textStyle = MaterialTheme.typography.body1,
            placeholder = {
                Text("Message")
            },
            leadingIcon = {
                IconButton(onClick = insertGif) {
                    Icon(
                        imageVector = Icons.Default.Gif,
                        contentDescription = null
                    )
                }
            },
            trailingIcon = {
                if (input.isEmpty()) {
                    Row {
                        IconButton(onClick = attachFile) {
                            Icon(
                                imageVector = Icons.Outlined.AttachFile,
                                contentDescription = null
                            )
                        }
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Outlined.Mic,
                                contentDescription = null
                            )
                        }
                    }
                } else {
                    IconButton(onClick = { sendMessage() }) {
                        Icon(
                            imageVector = Icons.Outlined.Send,
                            contentDescription = null
                        )
                    }
                }
            },
        )
    }
}

@Composable
fun ChatHistory(
    client: TelegramClient,
    messages: LazyPagingItems<TdApi.Message>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier, reverseLayout = true) {
        when {
            messages.loadState.refresh is LoadState.Loading -> {
                item {
                    CircularProgressIndicator() // TODO ChatLoading()
                }
            }

            messages.loadState.refresh is LoadState.Error -> {
                item {
                    Text(
                        text = "Cannot load messages",
                        // style = MaterialTheme.typography.h5,
                        modifier = modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    )
                }
            }

            messages.loadState.refresh is LoadState.NotLoading && messages.itemCount == 0 -> {
                item {
                    Text("Empty")
                }
            }
        }

        itemsIndexed(messages.itemSnapshotList.items) { i, message ->
            message.let {
                val userId = (message.senderId as TdApi.MessageSenderUser).userId
                val previousMessageUserId =
                    if (i > 0) (messages[i - 1]?.senderId as TdApi.MessageSenderUser?)?.userId else null
                MessageItem(
                    isSameUserFromPreviousMessage = userId == previousMessageUserId,
                    client = client,
                    message = it
                )
            }
        }
    }
}

@Composable
private fun MessageItem(
    isSameUserFromPreviousMessage: Boolean,
    client: TelegramClient,
    message: TdApi.Message,
    modifier: Modifier = Modifier
) {
    if (message.isOutgoing) {
        Box(
            Modifier
                .clickable(onClick = {})
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomEnd
        ) {
            MessageItemCard(modifier = Modifier.padding(8.dp, 4.dp, 8.dp, 4.dp)) {
                MessageItemContent(
                    client = client,
                    message = message,
                    modifier = Modifier
                        .background(Color.Green.copy(alpha = 0.2f))
                        .padding(8.dp)
                )
            }
        }
    } else {
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.clickable(onClick = {}) then modifier.fillMaxWidth()
        ) {
            if (!isSameUserFromPreviousMessage) {
                ChatUserIcon(
                    client = client,
                    userId = (message.senderId as TdApi.MessageSenderUser).userId,
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
                    client = client,
                    message = message,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
private fun ChatUserIcon(client: TelegramClient, userId: Long, modifier: Modifier) {
    val user = client.send<TdApi.User>(TdApi.GetUser(userId)).collectAsState(initial = null).value
    com.ilhomsoliev.shared.TelegramImage(
        client = client,
        file = user?.profilePhoto?.small,
        modifier = modifier
    )
}

@Composable
private fun MessageItemCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) = Card(
    // elevation = CardDefaults.elevatedShape,
    shape = RoundedCornerShape(8.dp),
    modifier = modifier,
    content = {
        content()
    }
)


@Composable
private fun MessageItemContent(
    client: TelegramClient,
    message: TdApi.Message,
    modifier: Modifier = Modifier
) {
    when (message.content) {
        is TdApi.MessageText -> TextMessage(message, modifier)
        is TdApi.MessageVideo -> VideoMessage(message, modifier)
        is TdApi.MessageCall -> CallMessage(message, modifier)
        is TdApi.MessageAudio -> AudioMessage(message, modifier)
        is TdApi.MessageSticker -> StickerMessage(client, message, modifier)
        is TdApi.MessageAnimation -> AnimationMessage(client, message, modifier)
        is TdApi.MessagePhoto -> PhotoMessage(client, message, Modifier)
        is TdApi.MessageVideoNote -> VideoNoteMessage(client, message, modifier)
        is TdApi.MessageVoiceNote -> VoiceNoteMessage(message, modifier)
        else -> UnsupportedMessage()
    }
}


