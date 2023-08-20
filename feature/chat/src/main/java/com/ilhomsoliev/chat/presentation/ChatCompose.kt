package com.ilhomsoliev.chat.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.ilhomsoliev.chat.R
import com.ilhomsoliev.chat.model.message.MessageModel
import com.ilhomsoliev.chat.presentation.message_item.MessageItem
import com.ilhomsoliev.shared.TelegramImage
import com.ilhomsoliev.shared.TgDownloadManager
import com.ilhomsoliev.shared.shared.PaperclipIcon
import com.ilhomsoliev.shared.shared.SendMessageIcon
import com.ilhomsoliev.shared.shared.SmileFaceIcon
import org.drinkless.td.libcore.telegram.TdApi

data class ChatState(
    val chat: TdApi.Chat? = null,
    val answer: String,
    val downloadManager: TgDownloadManager,
    val messages: LazyPagingItems<MessageModel>?,
)

interface ChatCallback {
    fun onAnswerChange(value: String)
    fun onSendMessage()
    fun onBack()

}

val chatElementsBackground = Color(0xFED9D9D9)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatContent(
    state: ChatState,
    callback: ChatCallback
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(chatElementsBackground),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        callback.onBack()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                    Column(modifier = Modifier.offset((-4).dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = state.chat?.title ?: "", style = TextStyle(
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight(600),
                                    color = Color(0xFF232323),
                                )
                            )
                            // TODO if notifcation is enabled
                        }

                        Text(
                            text = "last seen", style = TextStyle(
                                fontSize = 11.sp,
                                fontWeight = FontWeight(500),
                                color = Color(0xFF979797),
                            )
                        )

                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TelegramImage(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(32.dp),
                        downloadManager = state.downloadManager,
                        file = state.chat?.photo?.small
                    )

                    IconButton(onClick = {

                    }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                    }
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
                }, onInputChange = {
                    callback.onAnswerChange(it)
                })
        }
    ) {
        state.messages?.run {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.chat_background),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
                ChatHistory(
                    downloadManager = state.downloadManager,
                    messagesPaging = this@run,
                    modifier = Modifier
                        .fillMaxWidth(),
                    it,
                )
            }
        }
    }
}

@Composable
fun MessageInput(
    modifier: Modifier = Modifier,
    input: String,
    onInputChange: (String) -> Unit,
    insertGif: () -> Unit = {},
    attachFile: () -> Unit = {},
    sendMessage: () -> Unit,
) {
    Row(
        modifier = modifier
            .background(chatElementsBackground)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier.padding(6.dp),
            imageVector = PaperclipIcon,
            contentDescription = null
        )

        BasicTextField(
            modifier = Modifier.weight(1f),
            value = input, onValueChange = {
                onInputChange(it)
            }, maxLines = 5, decorationBox = {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0x1F767680))
                        .padding(vertical = 6.dp, horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        it()
                    }
                    Image(
                        modifier = Modifier.clickable {
                            //TODO
                        },
                        imageVector = SmileFaceIcon,
                        contentDescription = null
                    )
                }

            })
        AnimatedVisibility(visible = input.isNotBlank()) {
            Box(
                modifier = Modifier.align(Alignment.Bottom),
                contentAlignment = Alignment.BottomCenter
            ) {
                IconButton(onClick = { sendMessage() }) {
                    Image(
                        imageVector = SendMessageIcon,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
fun ChatHistory(
    downloadManager: TgDownloadManager,
    messagesPaging: LazyPagingItems<MessageModel>,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
) {
    val messages = messagesPaging.itemSnapshotList.items

    LazyColumn(modifier = modifier, reverseLayout = true) {
        item {
            Spacer(modifier = Modifier.height(paddingValues.calculateBottomPadding()))
        }
        when {
            messagesPaging.loadState.refresh is LoadState.Loading -> {
                item {
                    CircularProgressIndicator()
                }
            }

            messagesPaging.loadState.refresh is LoadState.Error -> {
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

            messagesPaging.loadState.refresh is LoadState.NotLoading && messagesPaging.itemCount == 0 -> {
                item {
                    Text("Empty")
                }
            }
        }

        itemsIndexed(messagesPaging.itemSnapshotList.items) { i, message ->
            message.let {
                val userId = message.sender?.id
                val previousMessageUserId = if (i > 0) messages[i - 1].sender?.id else null
                val nextMessage = messages.getOrNull(i + 1)?.sender?.id
                val previousMessage = messages.getOrNull(i - 1)?.sender?.id
                val isLastMessage = messages[i].sender?.id != nextMessage
                val isFirstMessage = messages[i].sender?.id != previousMessage
                MessageItem(
                    isSameUserFromPreviousMessage = userId == previousMessageUserId,
                    downloadManager = downloadManager,
                    message = it
                )
            }

        }

    }
}
